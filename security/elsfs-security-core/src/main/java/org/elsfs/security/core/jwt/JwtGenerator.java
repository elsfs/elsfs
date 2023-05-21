package org.elsfs.security.core.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;

/**
 * @author zeng
 */

public class JwtGenerator implements JwtTokenGenerator<Jwt> {

    private static final TemporalAmount DEFAULT_TIME_TO_LIVE = Duration.ofMinutes(30);

    private final JwtEncoder jwtEncoder;

    public JwtGenerator(JwtEncoder jwtEncoder) {
        Assert.notNull(jwtEncoder, "jwtEncoder cannot be null");
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public Jwt generate(JutTokenContext context) {
        String issuer = null;
        if (context.getAuthorizationServerSettings() != null) {
            issuer = context.getAuthorizationServerSettings().getIssuer();
        }
        var issuedAt = Instant.now();
        var expiresAt = issuedAt.plus(context.getTimeToLive() == null ? DEFAULT_TIME_TO_LIVE : context.getTimeToLive());

        // @formatter:off
        var claimsBuilder = context.getClaims() != null ? context.getClaims() : JwtClaimsSet.builder();
        var jwsAlgorithm = SignatureAlgorithm.RS256;
        var jwsHeaderBuilder = context.getJwsHeader() != null ? context.getJwsHeader() : JwsHeader.with(jwsAlgorithm);
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder
                .issuedAt(issuedAt) // jwt的发放时间，通常写当前时间的时间戳
                .expiresAt(expiresAt) // jwt的到期时间，通常写时间戳
                .notBefore(issuedAt)  // 一个时间点，在该时间点到达之前，这个令牌是不可用的
                //    .id() // jwt的唯一编号，设置此项的目的，主要是为了防止重放攻击（重放攻击是在某些场景下，用户使用之前的令牌发送到服务器，被服务器正确的识别，从而导致不可预期的行为发生）
                .audience(Collections.singletonList("login")); // 该jwt是发放给哪个终端的，可以是终端类型，也可以是用户名称，随意一点

        var userDetails = context.getUserDetails();
        if ( userDetails != null ) {
            if (StringUtils.hasText(userDetails.getUsername())){
                claimsBuilder.subject(context.getUserDetails().getUsername()); //该jwt是用于干嘛的
            }
            if (!CollectionUtils.isEmpty(userDetails.getAuthorities())){
                claimsBuilder.claim(OAuth2ParameterNames.SCOPE,
                        AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));
            }

        }
        if (context.getHttpSession() != null) {
            claimsBuilder.claim(IdTokenClaimNames.NONCE, context.getHttpSession().getId()); // 认证的Session ID
        }


        var jwsHeader = jwsHeaderBuilder.build();
        var claims = claimsBuilder.build();
        var jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        return jwt;
    }
}

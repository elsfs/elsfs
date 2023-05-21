package org.elsfs.admin.infrastructure.gatewayimpl;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.elsfs.admin.domain.gateway.LoginGateway;
import org.elsfs.admin.domain.login.LoginInfo;
import org.elsfs.admin.domain.login.LoginReturnInfo;
import org.elsfs.admin.domain.user.UserInfo;
import org.elsfs.admin.infrastructure.dataobject.SysUserDB;
import org.elsfs.admin.infrastructure.repository.SysUserRepository;
import org.elsfs.security.core.jwt.JutTokenContextImpl;
import org.elsfs.security.core.jwt.JwtGenerator;
import org.elsfs.security.core.userdetails.SecurityUser;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;

/**
 * @author zeng
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class LoginGatewayImpl implements LoginGateway {

    private final JWKSource<SecurityContext> jwkSource;

    private final AuthorizationServerSettings authorizationServerSettings;

    private final static Duration TOKEN_TIME_TO_LIVE = Duration.ofDays(1L);

    private final SysUserRepository sysUserRepository;

    private final PasswordEncoder passwordEncoder;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * 用户名密码登陆
     * @param request request
     * @param response response
     * @param loginInfo 登陆信息
     * @return 用户信息
     */
    @Override
    public LoginReturnInfo usernamePassword(HttpServletRequest request, HttpServletResponse response,
            LoginInfo.UsernamePassword loginInfo) {
        SysUserDB sysUserDB = sysUserRepository.findByUsername(loginInfo.getAccount().getUsername())
            .orElseThrow(() -> new BadCredentialsException(this.messages
                .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials")));
        SecurityUser securityUser = createSecurityUser(sysUserDB, AuthorityUtils.createAuthorityList("admin", "ins"));
        if (!passwordEncoder.matches(loginInfo.getEvidence().getPassword(), sysUserDB.getPassword())) {
            throw new BadCredentialsException(this.messages
                .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        successAuthentication(request, securityUser);
        Jwt token = generatorToken(securityUser, request);
        LoginReturnInfo info = new LoginReturnInfo()
            .setUserInfo(new UserInfo().setUserId(securityUser.getUserId())
                .setUsername(securityUser.getUsername())
                .setPhone(securityUser.getPhone())
                .setNickname(securityUser.getNickname())
                .setAvatar(securityUser.getAvatar()))
            .setToken(token.getTokenValue());
        return info;
    }

    /**
     * 创建 token
     * @param securityUser 用户信息
     * @param request request
     * @return Jwt
     */
    private Jwt generatorToken(SecurityUser securityUser, HttpServletRequest request) {
        JutTokenContextImpl tokenContext = JutTokenContextImpl
            .with(JwsHeader.with(SignatureAlgorithm.RS256), JwtClaimsSet.builder())
            .authorizationServerSettings(authorizationServerSettings)
            .httpSession(request.getSession())
            .userDetails(securityUser)
            .timeToLive(TOKEN_TIME_TO_LIVE)
            .build();
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        return jwtGenerator.generate(tokenContext);
    }

    /**
     * 创建成功的
     * @param securityUser 用户信息
     * @return AbstractAuthenticationToken
     */
    private void successAuthentication(HttpServletRequest request, SecurityUser securityUser) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(securityUser,
                null, securityUser.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static SecurityUser createSecurityUser(SysUserDB sysUserDB,
            Collection<GrantedAuthority> grantedAuthorities) {
        return new SecurityUser(sysUserDB.getUserId(), sysUserDB.getPhone(), sysUserDB.getEmail(), null,
                sysUserDB.getNickname(), grantedAuthorities, null, sysUserDB.getUsername(), null);
    }

}

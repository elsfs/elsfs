package org.elsfs.client;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * client_secret_jwt 模式
 */
class CLIENT_SECRET_JWT extends AbstractClientTypeAuthorizationServerTests {

    @Test
    @Override
    @Order(30)
    void exchange() {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION_TYPE,
                "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, ClientSecretJwtValues.clientId);
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION, createClientSecretJwtToken());
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrlStr())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(requestMap);
        // 3.响应体
        ResponseEntity<ResponseBody> exchange = restTemplate.exchange(request, ResponseBody.class);
        ResponseBody body = exchange.getBody();
        assertThat(body).isNotNull();
    }

    @Test
    @Order(29)
    void addRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(ClientSecretJwtValues.clientId)
            .clientSecret(ClientSecretJwtValues.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("message.read")
            .scope("message.write")
            .clientSettings(ClientSettings.builder()
                .tokenEndpointAuthenticationSigningAlgorithm(MacAlgorithm.HS512) // client_secret_jwt
                                                                                 // 需要
                // .tokenEndpointAuthenticationSigningAlgorithm(MacAlgorithm.HS512) //
                // private_key_jwt 需要
                .build())
            .tokenSettings(TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED) // REFERENCE 不需要设置
                                                                     // jwk SELF_CONTAINED
                                                                     // 需要设置 jwk
                .build())
            .build();
        registeredClientRepository.save(registeredClient);
    }

    private String createClientSecretJwtToken() {
        try {
            // algorithm 必须是 HMACSHA256
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    ClientSecretJwtValues.clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            JWSSigner signer = new MACSigner(secretKeySpec);
            // subject, issuer, audience, expirationTime 这四个参数是必须的
            // 服务器那边会校验
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                // 主题
                .subject(ClientSecretJwtValues.clientId)
                .jwtID(ClientSecretJwtValues.clientId)
                // 签发人
                .issuer(ClientSecretJwtValues.clientId)
                // 受众 必填 必须和 配置ISSUER一致
                .audience(authorizationServerSettings.getIssuer())
                // 过期时间
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 60 * 60 * 1000))
                .build();
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512, JOSEObjectType.JWT, null, null, null, null, null, null,
                    null, null, null, true, null, null);
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(signer);
            String token = signedJWT.serialize();
            return token;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    private interface ClientSecretJwtValues {

        String clientId = "client-secret-jwt";

        String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret");

    }

}

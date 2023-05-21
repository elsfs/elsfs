package org.elsfs.client;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;
import org.elsfs.entity.ResponseBody;
import org.elsfs.security.util.JwkGeneratorUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PRIVATE_KEY_JWT extends AbstractClientTypeAuthorizationServerTests {
    @Autowired
    JWKSet jwkSet;

    @Override
    @Test
    @Order(50)
    void exchange() {

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION_TYPE, "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, PrivateKeyJwtClientValues.clientId);
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION, createPrivateKeyJwtToken());
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, "client_credentials");

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(getUrlStr())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestMap);
        //3.响应体
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);
        assertThat(response.getBody()).isNotNull();
    }


    @Test
    @Order(49)
    void addRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(PrivateKeyJwtClientValues.clientId)
                .clientSecret(PrivateKeyJwtClientValues.clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder()
                        // .tokenEndpointAuthenticationSigningAlgorithm(MacAlgorithm.HS512) // client_secret_jwt 需要
                        .tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.RS512)  // private_key_jwt 需要
                        .jwkSetUrl(getJwkSetUrl()) // private_key_jwt 需要
                        .build())
                .tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE) // REFERENCE 不需要设置 jwk   SELF_CONTAINED 需要设置 jwk
                        .build())
                .build();
        registeredClientRepository.save(registeredClient);
    }

    private String createPrivateKeyJwtToken() {
        // 至少以下四项信息
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                // 主体：固定clientId
                .subject(PrivateKeyJwtClientValues.clientId)
                // 发行者：固定clientId
                .issuer(PrivateKeyJwtClientValues.clientId)
                // 授权中心的地址
                .audience("http://localhost:" + this.environment.getProperty("local.server.port", "8080"))
                // 过期时间 24h
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .build();


        SignedJWT signedJWT = null;
        try {
            List<JWK> keys = jwkSet.getKeys();
            if (keys == null || keys.isEmpty()) {
                throw new Exception("key不存在");
            }
            JWSHeader header = new JWSHeader(JWSAlgorithm.RS512,
                    JOSEObjectType.JWT,
                    null,
                    null, null
                    ,
                    null
                    , null,
                    null,
                    null, null, null, true, null, null
            );
            RSASSASigner signer = new RSASSASigner(keys.get(0).toRSAKey().toPrivateKey());

            signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(signer);

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String token = signedJWT.serialize();
        System.out.println(token);
        return token;
    }

    private interface PrivateKeyJwtClientValues {
        String clientId = "private-key-jwt";
        String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret");
    }


}

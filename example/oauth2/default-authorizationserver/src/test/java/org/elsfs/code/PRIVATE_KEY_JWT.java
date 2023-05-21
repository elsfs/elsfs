package org.elsfs.code;

import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.elsfs.entity.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PRIVATE_KEY_JWT extends AbstractCodeTypeAuthorizationServerTests{
    @Autowired
    JWKSet jwkSet;
    protected UnaryOperator<UriComponentsBuilder> getCodeHttpMethodGETUnaryOperator(){
        return t-> t .queryParam("client_id", PrivateKeyJwtClientValues.clientId);
    }
    protected List<NameValuePair> getCodeHttpMethodPOSTUnaryOperator( ){
        return Arrays.asList(new NameValuePair(OAuth2ParameterNames.CLIENT_ID, PrivateKeyJwtClientValues.clientId));
    }
    @Override
    protected ResponseEntity<ResponseBody> getToken(String code) {

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION_TYPE, "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, PrivateKeyJwtClientValues.clientId);
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION, createPrivateKeyJwtToken());

        requestMap.add(OAuth2ParameterNames.CODE, code);
        requestMap.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
        requestMap.add(PkceParameterNames.CODE_VERIFIER, CODE_VERIFIER_VALUE);
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(getUrl()+"/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestMap);
        //3.响应体
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);
        assertThat(response.getBody()).isNotNull();
        return response;
    }

    protected String getJwkSetUrl() {
        return "http://localhost:" + this.environment.getProperty("local.server.port", "8080") + "/oauth2/jwks";

    }


    void addRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(PrivateKeyJwtClientValues.clientId)
                .clientSecret(PrivateKeyJwtClientValues.clientSecret)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .redirectUri(redirectUri)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true)
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
        String clientId = "private-key-jwt-code";
        String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret");
    }

}

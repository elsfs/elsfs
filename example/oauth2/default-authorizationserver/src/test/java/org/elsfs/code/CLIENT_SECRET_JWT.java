package org.elsfs.code;

import com.gargoylesoftware.htmlunit.util.NameValuePair;
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
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class CLIENT_SECRET_JWT extends AbstractCodeTypeAuthorizationServerTests {

    protected UnaryOperator<UriComponentsBuilder> getCodeHttpMethodGETUnaryOperator() {
        return t -> t.queryParam("client_id", ClientSecretJwtValues.clientId);
    }

    protected List<NameValuePair> getCodeHttpMethodPOSTUnaryOperator() {
        return Arrays.asList(new NameValuePair(OAuth2ParameterNames.CLIENT_ID, ClientSecretJwtValues.clientId));
    }

    @Override
    protected ResponseEntity<ResponseBody> getToken(String code) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION_TYPE,
                "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, ClientSecretJwtValues.clientId);
        requestMap.add(OAuth2ParameterNames.CLIENT_ASSERTION, createClientSecretJwtToken());

        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        requestMap.add(OAuth2ParameterNames.CODE, code);
        requestMap.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
        requestMap.add(PkceParameterNames.CODE_VERIFIER, CODE_VERIFIER_VALUE);

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrl() + "/oauth2/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(requestMap);
        return restTemplate.exchange(request, ResponseBody.class);
    }

    void addRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(ClientSecretJwtValues.clientId)
            .clientSecret(ClientSecretJwtValues.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("message.read")
            .scope("message.write")
            .redirectUri(redirectUri)

            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(true)
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

        String clientId = "client-secret-code";

        String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret");

    }

}

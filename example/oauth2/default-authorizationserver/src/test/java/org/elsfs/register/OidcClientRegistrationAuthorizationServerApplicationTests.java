package org.elsfs.register;

import org.elsfs.code.AbstractCodeTypeAuthorizationServerTests;
import org.elsfs.code.CLIENT_SECRET_JWT;
import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
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
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientMetadataClaimNames;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zeng
 * 默认授权测试
 */

/**
 * 撤销token
 */
public class OidcClientRegistrationAuthorizationServerApplicationTests
        extends AbstractCodeTypeAuthorizationServerTests {

    @Override
    protected ResponseEntity<ResponseBody> getToken(String code) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, "messaging-client");
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, "authorization_code");
        requestMap.add(OAuth2ParameterNames.CODE, code);
        requestMap.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
        // BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
        requestMap.add(PkceParameterNames.CODE_VERIFIER, "ss");
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrl() + "/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(requestMap);
        return restTemplate.exchange(request, ResponseBody.class);
    }

    @Test
    @WithMockUser("admin")
    @Order(100)
    public void exchange() throws IOException {
        addRegisteredClientRepository();
        RequestEntity<Void> request = RequestEntity
            .post(UriComponentsBuilder.fromHttpUrl(getUrl())
                .path("/oauth2/token")
                .queryParam("scope", "client.create")
                .queryParam(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())
                .toUriString())
            .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder()
                .encodeToString((ClientSecretJwtValues.clientId + ":" + "secret").getBytes(StandardCharsets.UTF_8)))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .build();
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);
        assertThat(response.getBody()).isNotNull();
        register(response.getBody().getAccess_token());

        //
        // String methodGET =
        // getCodeHttpMethodGET(getCodeHttpMethodGETUnaryOperator().apply(getUriComponentsBuilder()));
        // ResponseEntity<ResponseBody> responseGET = getToken(methodGET);
        // assertThat(responseGET.getBody()).isNotNull();
        // System.out.println(responseGET.getBody());
    }

    private void register(String access_token) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        // requestMap.add(OidcClientMetadataClaimNames.CLIENT_ID_ISSUED_AT,
        // Instant.now());
        // requestMap.add(OidcClientMetadataClaimNames.CLIENT_SECRET_EXPIRES_AT,100000);

        requestMap.add(OidcClientMetadataClaimNames.CLIENT_SECRET, "secret");
        requestMap.add(OidcClientMetadataClaimNames.REDIRECT_URIS, redirectUri);
        requestMap.add(OidcClientMetadataClaimNames.SCOPE, "openid message.read message.write");

        requestMap.add(OAuth2ParameterNames.CLIENT_ID, "register");
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrl() + "/connect/register")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + access_token)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestMap);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        System.out.println(response.getBody());
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    // client.create
    protected void addRegisteredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(ClientSecretJwtValues.clientId)
            .clientSecret(ClientSecretJwtValues.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .scope("client.create")

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

    private interface ClientSecretJwtValues {

        String clientId = "client-secret-create";

        String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret");

    }

}
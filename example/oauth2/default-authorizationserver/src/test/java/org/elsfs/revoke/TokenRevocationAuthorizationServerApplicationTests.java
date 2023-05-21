package org.elsfs.revoke;

import org.elsfs.code.AbstractCodeTypeAuthorizationServerTests;
import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zeng
 * 默认授权测试
 */

/**
 * 撤销token
 */
public class TokenRevocationAuthorizationServerApplicationTests extends AbstractCodeTypeAuthorizationServerTests {

    @Override
    protected ResponseEntity<ResponseBody> getToken(String code) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, "messaging-client");
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, "authorization_code");
        requestMap.add(OAuth2ParameterNames.CODE, code);
        requestMap.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
        // BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
        requestMap.add(PkceParameterNames.CODE_VERIFIER, "ss");
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(getUrl() + "/oauth2/token")
                .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestMap);
        return restTemplate.exchange(request, ResponseBody.class);
    }

    @Test
    @WithMockUser("admin")
    @Order(100)
    public void exchange() throws IOException {
        String methodGET = getCodeHttpMethodGET(getCodeHttpMethodGETUnaryOperator().apply(getUriComponentsBuilder()));
        ResponseEntity<ResponseBody> responseGET = getToken(methodGET);
        assertThat(responseGET.getBody()).isNotNull();
        System.out.println(responseGET.getBody());
        tokenRevocationA(responseGET.getBody().getAccess_token());
    }

    private void tokenRevocationA(String access_token) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.TOKEN, access_token);
        requestMap.add(OAuth2ParameterNames.TOKEN_TYPE_HINT, "access_token");
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(getUrl() + "/oauth2/revoke")
                .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestMap);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        // {"active":true,"sub":"admin","aud":["messaging-client"],"nbf":1682348184,"scope":"openid message.read message.write","iss":"http://localhost:0",
        // "exp":1682348484,"iat":1682348184,"client_id":"messaging-client","token_type":"Bearer"}
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }
}
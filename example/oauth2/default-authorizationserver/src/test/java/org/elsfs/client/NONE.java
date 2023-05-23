package org.elsfs.client;

import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.SecureRandom;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * None 模式主要真对code模式
 */
class NONE extends AbstractClientTypeAuthorizationServerTests {

    @Test
    @Override
    @Order(20)
    void exchange() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, CLIENT_ID);
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, "client_credentials");
        requestMap.add(PkceParameterNames.CODE_VERIFIER, verifier);
        // code_challenge_method 可以设置为 plain (原始值) 或者 S256 (sha256哈希)。
        // requestMap.add(PkceParameterNames.CODE_CHALLENGE_METHOD, "plain");
        // 使用 code_challenge_method 对 code_verifier 进行转换得到 code_challenge， 可以使用下面的方式进行转换
        // plain
        // code_challenge = code_verifier
        // S256
        // code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
        // requestMap.add(PkceParameterNames.CODE_CHALLENGE, verifier);

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrlStr())
            .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(requestMap);
        // 3.响应体
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);

        assertThat(response.getBody()).isNotNull();
    }

}

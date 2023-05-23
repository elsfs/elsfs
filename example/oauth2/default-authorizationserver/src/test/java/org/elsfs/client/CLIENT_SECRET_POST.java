package org.elsfs.client;

import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

class CLIENT_SECRET_POST extends AbstractClientTypeAuthorizationServerTests {

    @Test
    @Order(40)
    @Override
    void exchange() {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, CLIENT_ID);
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, "client_credentials");
        requestMap.add(OAuth2ParameterNames.CLIENT_SECRET, CLIENT_SECRET);
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrlStr())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(requestMap);
        // 3.响应体
        ResponseEntity<ResponseBody> exchange = restTemplate.exchange(request, ResponseBody.class);
        ResponseBody body = exchange.getBody();
        assertThat(body).isNotNull();
    }

}

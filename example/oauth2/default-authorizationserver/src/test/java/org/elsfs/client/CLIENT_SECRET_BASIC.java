package org.elsfs.client;

import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * 客户端模式 basic方式提交认证
 * @see  ClientAuthenticationMethod#CLIENT_SECRET_BASIC
 */
 class CLIENT_SECRET_BASIC extends AbstractClientTypeAuthorizationServerTests {

    @Test
    @Order(10)
    public void exchange() {
        RequestEntity<Void> request = RequestEntity
                .post(getUriComponentsBuilder().toUriString())
                .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .build();
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);
        assertThat(response.getBody()).isNotNull();
    }
}
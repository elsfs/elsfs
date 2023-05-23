package org.elsfs.refreshtoken;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.elsfs.code.AbstractCodeTypeAuthorizationServerTests;
import org.elsfs.code.CLIENT_SECRET_BASIC;
import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zeng 默认授权测试
 */

public class RefreshTokenAuthorizationServerApplicationTests extends AbstractCodeTypeAuthorizationServerTests {

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
        String methodGET = getCodeHttpMethodGET(getCodeHttpMethodGETUnaryOperator().apply(getUriComponentsBuilder()));
        ResponseEntity<ResponseBody> responseGET = getToken(methodGET);
        assertThat(responseGET.getBody()).isNotNull();
        System.out.println(responseGET.getBody());
        refreshToken(responseGET.getBody().getRefresh_token());
    }

    private void refreshToken(String refreshToken) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.REFRESH_TOKEN.getValue());
        requestMap.add(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken);
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity.post(getUrl() + "/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(requestMap);
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);
        assertThat(response.getBody()).isNotNull();
        System.out.println(response.getBody());
    }

}
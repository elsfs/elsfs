package org.elsfs.code;

import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class CLIENT_SECRET_BASIC extends AbstractCodeTypeAuthorizationServerTests {

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



 }

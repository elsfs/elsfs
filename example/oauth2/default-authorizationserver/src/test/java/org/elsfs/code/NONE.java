package org.elsfs.code;

import org.elsfs.entity.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.SecureRandom;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class NONE extends AbstractCodeTypeAuthorizationServerTests{
    @Override
    protected ResponseEntity<ResponseBody> getToken(String code) {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add(OAuth2ParameterNames.CLIENT_ID, "messaging-client");
        requestMap.add(OAuth2ParameterNames.SCOPE, "openid profile message.read message.write");

        requestMap.add(OAuth2ParameterNames.CODE, code);
        requestMap.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
        requestMap.add(PkceParameterNames.CODE_VERIFIER, CODE_VERIFIER_VALUE);
        requestMap.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());



        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(getUrl()+"/oauth2/token")
                .header(HttpHeaders.AUTHORIZATION, BASIC_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestMap);
        //3.响应体
        ResponseEntity<ResponseBody> response = restTemplate.exchange(request, ResponseBody.class);

        return response;
    }
}

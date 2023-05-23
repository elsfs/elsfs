package org.elsfs.code;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.nimbusds.jose.util.Base64URL;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.tsp.TSPUtil;
import org.elsfs.entity.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractCodeTypeAuthorizationServerTests {

    protected static String CODE_VERIFIER_VALUE = "ss";
    protected static String CODE_CHALLENGE_VAlUE = Base64URL.encode(getSha256Str(CODE_VERIFIER_VALUE)).toString();
    protected static String BASIC_TOKEN = "Basic " + Base64.getEncoder().encodeToString(
            ("messaging-client:secret").getBytes(StandardCharsets.UTF_8));
    @MockBean
    private OAuth2AuthorizationConsentService authorizationConsentService;
    @Autowired
    protected RegisteredClientRepository registeredClientRepository;
    @Autowired
    protected AuthorizationServerSettings authorizationServerSettings;
    @Autowired
 protected   Environment environment;
    protected final String redirectUri = "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc";
    protected RestTemplate restTemplate = new RestTemplate();
    @Autowired
    protected WebClient webClient;




    @BeforeEach
    public void setUp() {
        this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        this.webClient.getOptions().setRedirectEnabled(true);
        this.webClient.getCookieManager().clearCookies();
        when(this.authorizationConsentService.findById(any(), any())).thenReturn(null);
    }

    protected String getUrl() {
        return "http://localhost:" + environment.getProperty("local.server.port", "9001");
    }

    protected String getCodeHttpMethodGET(UriComponentsBuilder uriComponentsBuilder) throws IOException {
        final HtmlPage consentPage = this.webClient.getPage(uriComponentsBuilder.toUriString());
        return assertThatHtmlPage(consentPage);
    }
    protected UnaryOperator<UriComponentsBuilder> getCodeHttpMethodGETUnaryOperator(){
        return t-> t .queryParam("client_id", "messaging-client");
    }
    protected String getCodeHttpMethodPOST(List<NameValuePair> nameValuePairs) throws IOException {
        WebRequest request = new WebRequest(new URL(getUrl() + "/oauth2/authorize"), HttpMethod.POST);
        request.setRequestParameters(nameValuePairs);
        final HtmlPage consentPage = this.webClient.getPage(request);
        return assertThatHtmlPage(consentPage);
    }
    protected List<NameValuePair> getCodeHttpMethodPOSTUnaryOperator( ){
     return Arrays.asList(new NameValuePair(OAuth2ParameterNames.CLIENT_ID, "messaging-client"));
    }

    protected abstract ResponseEntity<ResponseBody> getToken(String code);
    void addRegisteredClientRepository(){}
    @Test
    @WithMockUser("admin")
    @Order(100)
    protected void exchange() throws IOException {
        addRegisteredClientRepository();
        String methodGET = getCodeHttpMethodGET(getCodeHttpMethodGETUnaryOperator().apply(getUriComponentsBuilder()));
        ResponseEntity<ResponseBody> responseGET = getToken(methodGET);
        assertThat(responseGET.getBody()).isNotNull();
        System.out.println(responseGET.getBody());

        String methodPOST = getCodeHttpMethodPOST(getNameValuePairs(getCodeHttpMethodPOSTUnaryOperator()));
        ResponseEntity<ResponseBody> responsePODT = getToken(methodPOST);
        System.out.println(responsePODT.getBody());
        assertThat(responsePODT.getBody()).isNotNull();
    }

    private  List<NameValuePair> getNameValuePairs( List<NameValuePair> list){
        List<NameValuePair> nameValuePairs = Arrays.asList(
                new NameValuePair(OAuth2ParameterNames.RESPONSE_TYPE, "code"),
                new NameValuePair(OAuth2ParameterNames.SCOPE, "openid message.read message.write"),
                new NameValuePair(OAuth2ParameterNames.REDIRECT_URI, redirectUri),
                new NameValuePair(OAuth2ParameterNames.STATE, "state"), // 可选
                new NameValuePair(PkceParameterNames.CODE_CHALLENGE, CODE_CHALLENGE_VAlUE), // 可选 CODE_CHALLENGE CODE_CHALLENGE_METHOD 一组
                new NameValuePair(PkceParameterNames.CODE_CHALLENGE_METHOD, "S256") // 可选 CODE_CHALLENGE CODE_CHALLENGE_METHOD 一组 固定值 S256
        );
        ArrayList<NameValuePair> arrayList = new ArrayList<>(nameValuePairs);
        if (!CollectionUtils.isEmpty(list)){
            arrayList.addAll(list);
        }
        return arrayList;
    }



    protected   UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder
                .fromPath("/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("scope", "openid message.read message.write")
                .queryParam("state", "state")
                .queryParam("redirect_uri", this.redirectUri)
                .queryParam(PkceParameterNames.CODE_CHALLENGE, CODE_CHALLENGE_VAlUE) // 可选 CODE_CHALLENGE CODE_CHALLENGE_METHOD 一组
                .queryParam(PkceParameterNames.CODE_CHALLENGE_METHOD, "S256") // 可选 CODE_CHALLENGE CODE_CHALLENGE_METHOD 一组 固定值 S256
                ;
    }


    private String assertThatHtmlPage(HtmlPage consentPage) throws IOException {
        assertThat(consentPage.getTitleText()).isEqualTo("Consent required");
        List<HtmlCheckBoxInput> scopes = new ArrayList<>();
        consentPage.querySelectorAll("input[name='scope']").forEach(scope ->
                scopes.add((HtmlCheckBoxInput) scope));
        for (HtmlCheckBoxInput scope : scopes) {
            scope.click();
        }
        List<String> scopeIds = new ArrayList<>();
        scopes.forEach(scope -> {
            assertThat(scope.isChecked()).isTrue();
            scopeIds.add(scope.getId());
        });
        assertThat(scopeIds).containsExactlyInAnyOrder("message.read", "message.write");

        DomElement submitConsentButton = consentPage.querySelector("button[id='submit-consent']");
        this.webClient.getOptions().setRedirectEnabled(false);
        WebResponse approveConsentResponse = submitConsentButton.click().getWebResponse();
        assertThat(approveConsentResponse.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY.value());
        String location = approveConsentResponse.getResponseHeaderValue("location");
        assertThat(location).startsWith(this.redirectUri);
        assertThat(location).contains("code=");
        return location.split("code=")[1].split("&")[0];
    }


    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static byte[] getSha256Str(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.US_ASCII));
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

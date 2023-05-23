package org.elsfs.client;

import com.gargoylesoftware.htmlunit.WebClient;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * 客户端模式测试
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
abstract class AbstractClientTypeAuthorizationServerTests {

    protected static String CLIENT_ID = "messaging-client";

    protected static String CLIENT_SECRET = "secret";

    protected static String PATH = "/oauth2/token";

    protected static String BASIC_TOKEN = "Basic "
            + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));

    protected RestTemplate restTemplate = new RestTemplate();

    @Autowired
    protected Environment environment;

    @Autowired
    protected RegisteredClientRepository registeredClientRepository;

    @Autowired
    protected AuthorizationServerSettings authorizationServerSettings;

    @Autowired(required = false)
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        if (passwordEncoder == null) {
            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    protected UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder
            .fromHttpUrl("http://localhost:" + this.environment.getProperty("local.server.port", "8080"))
            .path(PATH)
            .queryParam(OAuth2ParameterNames.GRANT_TYPE, "client_credentials")
            .queryParam(OAuth2ParameterNames.CLIENT_ID, CLIENT_ID)
            .queryParam(OAuth2ParameterNames.STATE, "some-state"); // 可选
    }

    abstract void exchange();

    protected String getUrlStr() {
        return "http://localhost:" + this.environment.getProperty("local.server.port", "8080") + PATH;
    }

    protected String getJwkSetUrl() {
        return "http://localhost:" + this.environment.getProperty("local.server.port", "8080") + "/oauth2/jwks";

    }

}

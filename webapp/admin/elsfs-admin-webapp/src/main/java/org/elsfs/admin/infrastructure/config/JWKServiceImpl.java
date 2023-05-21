package org.elsfs.admin.infrastructure.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.elsfs.security.core.properties.PermitAllUrlProperties;
import org.elsfs.security.core.service.JWKService;
import org.elsfs.security.util.SecurityReadKeyUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class JWKServiceImpl implements JWKService {

    private final PermitAllUrlProperties permitAllUrlProperties;

    /**
     * @return
     */
    @Override
    public List<JWK> getJWTs() {
        try {
            InputStream publicKey = new ClassPathResource(permitAllUrlProperties.getPublicKeyPath()).getInputStream();
            InputStream privateKey = new ClassPathResource(permitAllUrlProperties.getPrivateKeyPath()).getInputStream();
            RSAPublicKey pemPublicKey = (RSAPublicKey) SecurityReadKeyUtils.readFileSuffixPemPublicKey(publicKey);
            PrivateKey pemPrivateKey = SecurityReadKeyUtils.readFileSuffixPemPrivateKey(privateKey);
            ArrayList<JWK> list = new ArrayList<>();
            RSAKey rsaKey = new RSAKey.Builder(pemPublicKey).privateKey(pemPrivateKey).keyID("www.elsfs.org").build();
            list.add(rsaKey);
            return list;
        }
        catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}

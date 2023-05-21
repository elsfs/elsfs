package org.elsfs.security.core.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.elsfs.security.core.service.JWKService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author zeng
 * @since 0.0.1
 */
@ConditionalOnBean(JWKService.class)
public class JwkSourceConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKService jwkService) {
        Assert.notEmpty(jwkService.getJWTs(), "jwkService.getJWTs() is not ！");
        List<JWK> jwks = jwkService.getJWTs();
        JWKSet jwkSet = new JWKSet(jwks);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

}

package org.elsfs.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.elsfs.security.util.JwkGeneratorUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

/**
 * JWK 来源配置
 */
@Configuration(proxyBeanMethods = false)
public class JWKSourceConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey rsaKey = JwkGeneratorUtils.generateRsa();
        return new JWKSet(rsaKey);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

}

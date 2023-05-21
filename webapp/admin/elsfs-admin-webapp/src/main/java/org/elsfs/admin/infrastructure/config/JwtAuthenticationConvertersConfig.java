package org.elsfs.admin.infrastructure.config;

import org.elsfs.admin.infrastructure.gatewayimpl.UserDetailsServiceImpl;
import org.elsfs.admin.infrastructure.repository.SecurityUserCacheRepository;

import org.elsfs.admin.infrastructure.repository.noop.NoopSecurityUserCacheRepository;
import org.elsfs.security.core.jwt.resource.JwtAuthenticationConvertersAdapter;
import org.elsfs.security.core.userdetails.SecurityUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author zeng
 * @since 0.0.1
 */
@Configuration
@ConditionalOnBean(UserDetailsServiceImpl.class)
public class JwtAuthenticationConvertersConfig {

    @Bean
    public JwtAuthenticationConvertersAdapter<UsernamePasswordAuthenticationToken> JwtAuthenticationConverter(
            UserDetailsService userDetailsService) {
        return new JwtAuthenticationConvertersAdapter<>((s) -> {
            SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(s.getSubject());
            userDetails.eraseCredentials(); // 必须抹除密码，因为已经认证过
            return UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
        });
    }

    @ConditionalOnMissingBean(SecurityUserCacheRepository.class)
    public static class SecurityUserCacheRepositoryConfig {

        @Bean
        public SecurityUserCacheRepository securityUserCacheRepository() {
            return new NoopSecurityUserCacheRepository();
        }

    }

}

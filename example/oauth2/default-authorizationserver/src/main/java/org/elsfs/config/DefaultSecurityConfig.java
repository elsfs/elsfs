package org.elsfs.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class DefaultSecurityConfig {
    private final String[] requestMatchers=new String[]{
            "/actuator/**", "/css/**", "/error","templates/**",
            "/assets/**", "/webjars/**", "/login",
            "/js/**","/img/**","/favicon.ico","**.css","**.js"
    };
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .and()
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(requestMatchers)
                                .permitAll()
                                .requestMatchers
                                        (PathRequest.toH2Console())
                                .permitAll()
                                .anyRequest().authenticated()
                )
              //  .userDetailsService(http.getSharedObject(UserDetailsService.class))

               ;
        return http.build();
    }

}

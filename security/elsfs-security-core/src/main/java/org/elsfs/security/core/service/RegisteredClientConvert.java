package org.elsfs.security.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.elsfs.security.core.entity.SecurityRegisteredClient;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.Arrays;
import java.util.Map;

/**
 * @author zeng
 * @since 0.0.1
 */
class RegisteredClientConvert {

    private final static ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
        .modules(new OAuth2AuthorizationServerJackson2Module())
        .modules(new JavaTimeModule())
        .build();

    public static RegisteredClient toServiceObject(SecurityRegisteredClient s) throws JsonProcessingException {
        RegisteredClient.Builder builder = RegisteredClient.withId(s.getId())
            .clientId(s.getClientId())
            .clientIdIssuedAt(s.getClientIdIssuedAt())
            .clientSecret(s.getClientSecret())
            .clientSecretExpiresAt(s.getClientSecretExpiresAt())
            .clientName(s.getClientName())
            .redirectUris(set -> set.addAll(Arrays.stream(StringUtils.split(s.getRedirectUris(), ",")).toList()))
            .scopes(set -> set.addAll(Arrays.stream(StringUtils.split(s.getScopes(), ",")).toList()))
            .clientSettings(
                    ClientSettings.withSettings(objectMapper.readValue(s.getClientSettings(), Map.class)).build())
            .clientAuthenticationMethods(
                    set -> set.addAll(Arrays.stream(StringUtils.split(s.getClientAuthenticationMethods(), ""))
                        .map(ClientAuthenticationMethod::new)
                        .toList()))
            .authorizationGrantTypes(
                    set -> set.addAll(Arrays.stream(StringUtils.split(s.getAuthorizationGrantTypes(), ","))
                        .map(AuthorizationGrantType::new)
                        .toList()))
            .tokenSettings(TokenSettings.withSettings(objectMapper.readValue(s.getTokenSettings(), Map.class)).build());
        return builder.build();
    }

    public static SecurityRegisteredClient toServiceObject(RegisteredClient s) throws JsonProcessingException {
        SecurityRegisteredClient client = new SecurityRegisteredClient();
        client.setId(s.getId());
        client.setClientId(s.getClientId());
        client.setClientIdIssuedAt(s.getClientIdIssuedAt());
        client.setClientSecret(s.getClientSecret());
        client.setClientSecretExpiresAt(s.getClientSecretExpiresAt());
        client.setClientName(s.getClientName());
        client.setClientAuthenticationMethods(StringUtils
            .join(s.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).toList(), ","));
        client.setAuthorizationGrantTypes(StringUtils
            .join(s.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).toList(), ","));
        client.setRedirectUris(StringUtils.join(s.getRedirectUris(), ","));
        client.setScopes(StringUtils.join(s.getScopes(), ","));
        client.setClientSettings(objectMapper.writeValueAsString(s.getClientSettings().getSettings()));
        client.setTokenSettings(objectMapper.writeValueAsString(s.getTokenSettings().getSettings()));
        return client;
    }

}

package org.elsfs.security.core.entity;

import java.time.Instant;
import java.util.Objects;

/**
 * oauth2 服务端 客户端明显
 *
 * @author zeng
 * @since 0.0.1
 */
public class SecurityRegisteredClient {

    private String id;

    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    private String clientName;

    /**
     * String “,”逗号分割
     */
    private String clientAuthenticationMethods;

    /**
     * String “,”逗号分割
     */
    private String authorizationGrantTypes;

    @Override
    public String toString() {
        return "SecurityRegisteredClient{" + "id='" + id + '\'' + ", clientId='" + clientId + '\''
                + ", clientIdIssuedAt=" + clientIdIssuedAt + ", clientSecret='" + clientSecret + '\''
                + ", clientSecretExpiresAt=" + clientSecretExpiresAt + ", clientName='" + clientName + '\''
                + ", clientAuthenticationMethods='" + clientAuthenticationMethods + '\'' + ", authorizationGrantTypes='"
                + authorizationGrantTypes + '\'' + ", redirectUris='" + redirectUris + '\'' + ", scopes='" + scopes
                + '\'' + ", clientSettings='" + clientSettings + '\'' + ", tokenSettings='" + tokenSettings + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityRegisteredClient that = (SecurityRegisteredClient) o;
        return id.equals(that.id) && clientId.equals(that.clientId) && clientIdIssuedAt.equals(that.clientIdIssuedAt)
                && clientSecret.equals(that.clientSecret) && clientSecretExpiresAt.equals(that.clientSecretExpiresAt)
                && clientName.equals(that.clientName)
                && clientAuthenticationMethods.equals(that.clientAuthenticationMethods)
                && authorizationGrantTypes.equals(that.authorizationGrantTypes)
                && redirectUris.equals(that.redirectUris) && scopes.equals(that.scopes)
                && clientSettings.equals(that.clientSettings) && tokenSettings.equals(that.tokenSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, clientIdIssuedAt, clientSecret, clientSecretExpiresAt, clientName,
                clientAuthenticationMethods, authorizationGrantTypes, redirectUris, scopes, clientSettings,
                tokenSettings);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public void setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
    }

    public String getTokenSettings() {
        return tokenSettings;
    }

    public void setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
    }

    /**
     * String “,”逗号分割
     */
    private String redirectUris;

    private String scopes;

    private String clientSettings;

    private String tokenSettings;

}

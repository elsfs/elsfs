package org.elsfs.security.core.service;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * TODO 转换器
 *
 * @author zeng
 * @since 0.0.1
 */

public class SecurityOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    public final SecurityAuthorizationConsentService securityOAuthorizationConsentService;

    public SecurityOAuth2AuthorizationConsentService(
            SecurityAuthorizationConsentService securityOAuthorizationConsentService) {
        this.securityOAuthorizationConsentService = securityOAuthorizationConsentService;
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
    }

    /**
     * @param authorizationConsent the {@link OAuth2AuthorizationConsent}
     */
    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return null;
    }

}

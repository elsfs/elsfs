package org.elsfs.security.core.service;

import org.elsfs.security.core.entity.SecurityOAuth2AuthorizationConsent;

public interface SecurityAuthorizationConsentService {

    void save(SecurityOAuth2AuthorizationConsent authorizationConsent);

    void remove(SecurityOAuth2AuthorizationConsent authorizationConsent);

    SecurityOAuth2AuthorizationConsent findById(String registeredClientId, String principalName);

}

package org.elsfs.security.core.entity;

import java.util.Objects;

/**
 * 授权同意明细
 *
 * @author zeng
 * @since 0.0.1
 */
public class SecurityOAuth2AuthorizationConsent {

    private static final String AUTHORITIES_SCOPE_PREFIX = "SCOPE_";

    /**
     * client_id
     */
    private String registeredClientId;

    /**
     * principal Name
     */
    private String principalName;

    /**
     * Set<GrantedAuthority>
     */
    private String authorities;

    @Override
    public String toString() {
        return "SecurityOAuth2AuthorizationConsent{" + "registeredClientId='" + registeredClientId + '\''
                + ", principalName='" + principalName + '\'' + ", authorities='" + authorities + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityOAuth2AuthorizationConsent that = (SecurityOAuth2AuthorizationConsent) o;
        return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName)
                && authorities.equals(that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredClientId, principalName, authorities);
    }

    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

}

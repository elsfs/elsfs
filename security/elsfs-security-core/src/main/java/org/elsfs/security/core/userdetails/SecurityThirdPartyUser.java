package org.elsfs.security.core.userdetails;

import java.util.Map;
import java.util.Objects;

/**
 * @author zeng
 * @since 0.0.1
 */

public class SecurityThirdPartyUser implements ThirdPartyUserDetails {

    /**
     * 第三方登陆元数据
     */
    private Map<String, Object> metadata;

    /**
     * 唯一标识属性名称
     */
    private String userNameAttribute;

    /**
     * 客户端id
     */
    private String clientId;

    private SecurityThirdPartyUser() {
    }

    public SecurityThirdPartyUser(Map<String, Object> metadata, String userNameAttribute, String clientId) {
        this.metadata = metadata;
        this.userNameAttribute = userNameAttribute;
        this.clientId = clientId;
    }

    public String getThirdPartyUserId() {
        if (metadata != null) {
            return metadata.get(getUserNameAttribute()).toString();
        }
        return null;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getUserNameAttribute() {
        return userNameAttribute;
    }

    public void setUserNameAttribute(String userNameAttribute) {
        this.userNameAttribute = userNameAttribute;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityThirdPartyUser that = (SecurityThirdPartyUser) o;
        return Objects.equals(metadata, that.metadata) && Objects.equals(userNameAttribute, that.userNameAttribute)
                && Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata, userNameAttribute, clientId);
    }

    @Override
    public String toString() {
        return "SecurityThirdPartyUser{" + "metadata=" + metadata + ", userNameAttribute='" + userNameAttribute + '\''
                + ", clientId='" + clientId + '\'' + '}';
    }

}

package org.elsfs.security.core.userdetails;

import java.io.Serializable;
import java.util.Map;

/**
 * 第三方用户
 *
 * @author zeng
 * @since 0.0.1
 */
public interface ThirdPartyUserDetails extends Serializable {

    /**
     * 第三方登陆元数据
     */
    Map<String, Object> getMetadata();

    /**
     * 唯一标识属性名称
     */
    String getUserNameAttribute();

    /**
     * 客户端id
     */
    String getClientId();

    default String getThirdPartyUserId() {
        if (getUserNameAttribute() != null && getMetadata() != null && getMetadata().isEmpty()) {
            return getMetadata().get(getUserNameAttribute()).toString();
        }
        return null;
    }

}

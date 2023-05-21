package org.elsfs.security.core.userdetails;

import java.io.Serializable;

/**
 * @author zeng
 */
public interface UserExtension extends Serializable {

    /**
     * 获取用户id
     * @return 用户唯一标识 不可作为登陆凭证
     */
    String getUserId();

    /**
     * 用户手机号
     * @return 用户唯一标识 可以作为唯一标识
     */
    String getPhone();

    /**
     * 用户邮箱
     * @return 用户唯一标识 可以作为唯一标识
     */
    String getEmail();

    /**
     * 用户名
     * @return 用户唯一标识 可以作为唯一标识
     */
    String getUsername();

    /**
     * 是否用户子账号
     * @return true 是 false 不是
     */
    Boolean isSubAccount();

    /**
     * 用户头像
     * @return
     */
    String getAvatar();

    /**
     * 获取父账号标识
     * @return
     */
    default String getParentAccount() {
        return isSubAccount() ? getUserId() : null;
    }

    /**
     * 昵称
     * @return 用户昵称
     */
    String getNickname();

}

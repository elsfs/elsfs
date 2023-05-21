package org.elsfs.admin.domain.gateway;

import org.elsfs.admin.domain.user.UserInfo;

/**
 * 系统用户相关
 */
public interface UserGateway {

    /**
     * 根据用户查询账号信息
     * @param username 用户名
     * @return 用户信息
     */
    UserInfo queryUserInfoByUsername(String username);

    /**
     * 根据用户手机号查询用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    UserInfo queryUserInfoByPhone(String phone);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfo queryUserInfoByUserId(String userId);

    /**
     * 根据用户邮箱查询手机号
     * @param email 邮箱
     * @return 用户信息
     */
    UserInfo queryUserInfoByEmail(String email);

}

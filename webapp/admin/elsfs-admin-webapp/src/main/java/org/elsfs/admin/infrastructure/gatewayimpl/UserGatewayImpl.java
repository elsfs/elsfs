package org.elsfs.admin.infrastructure.gatewayimpl;

import org.elsfs.admin.domain.gateway.UserGateway;
import org.elsfs.admin.domain.user.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author zeng
 * @since 0.0.1
 */
@Service
public class UserGatewayImpl implements UserGateway {

    /**
     * 根据用户查询账号信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserInfo queryUserInfoByUsername(String username) {
        return null;
    }

    /**
     * 根据用户手机号查询用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    @Override
    public UserInfo queryUserInfoByPhone(String phone) {
        return null;
    }

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public UserInfo queryUserInfoByUserId(String userId) {
        return null;
    }

    /**
     * 根据用户邮箱查询手机号
     * @param email 邮箱
     * @return 用户信息
     */
    @Override
    public UserInfo queryUserInfoByEmail(String email) {
        return null;
    }

}

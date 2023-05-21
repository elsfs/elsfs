package org.elsfs.security.core.service;

import org.elsfs.security.core.exception.ThirdPartyException;
import org.elsfs.security.core.userdetails.UserExtension;
import org.elsfs.security.core.userdetails.ThirdPartyUserDetails;

/**
 * 第三方登陆服务
 *
 * @author zeng
 * @since 0.0.1
 *
 */
@FunctionalInterface
public interface ThirdPartyUserService {

    /**
     * 根据唯一标识找到第三方用户信息
     */
    UserExtension load(ThirdPartyUserDetails thirdPartyUserDetails, Filter filter) throws ThirdPartyException;

    @FunctionalInterface
    interface Filter {

        /**
         * 如果为 false则不查询
         */
        boolean filter(ThirdPartyUserDetails user);

    }

}

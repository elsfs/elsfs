package org.elsfs.admin.domain.gateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elsfs.admin.domain.login.LoginInfo;
import org.elsfs.admin.domain.login.LoginReturnInfo;

/**
 * 登陆
 *
 * @author zeng
 * @since 0.0.1
 */
public interface LoginGateway {

    /**
     * 用户名密码登陆
     * @param request request
     * @param response response
     * @param loginInfo 登陆信息
     * @return 用户信息
     */
    LoginReturnInfo usernamePassword(HttpServletRequest request, HttpServletResponse response,
            LoginInfo.UsernamePassword loginInfo);

}

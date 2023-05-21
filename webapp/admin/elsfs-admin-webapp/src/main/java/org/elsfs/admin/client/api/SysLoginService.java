package org.elsfs.admin.client.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elsfs.admin.client.dto.clientobject.SysLoginCO;
import org.elsfs.admin.client.dto.login.UsernamePasswordLoginCmd;
import org.elsfs.core.dto.SingleResponse;

/**
 * 系统登陆相关接口
 *
 * @author zeng
 * @serial 0.0.1
 */
public interface SysLoginService {

    /**
     * @param usernamePasswordLoginQry 用户名密码登陆
     * @return 用户信息和token
     */
    SingleResponse<SysLoginCO> usernamePasswordLogin(HttpServletRequest httpServletRequest, HttpServletResponse request,
            UsernamePasswordLoginCmd usernamePasswordLoginQry);

}

package org.elsfs.admin.app.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.elsfs.admin.app.command.UsernamePasswordLoginCmdExe;
import org.elsfs.admin.client.api.SysLoginService;
import org.elsfs.admin.client.dto.clientobject.SysLoginCO;
import org.elsfs.admin.client.dto.login.UsernamePasswordLoginCmd;
import org.elsfs.core.dto.SingleResponse;
import org.springframework.stereotype.Service;

/**
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@Service
public class SysLoginServiceImpl implements SysLoginService {

    private final UsernamePasswordLoginCmdExe usernamePasswordLoginCmdExe;

    /**
     * @param usernamePasswordLoginCmd 用户名密码登陆
     * @return
     */
    @Override
    public SingleResponse<SysLoginCO> usernamePasswordLogin(HttpServletRequest httpServletRequest,
            HttpServletResponse request, UsernamePasswordLoginCmd usernamePasswordLoginCmd) {
        return usernamePasswordLoginCmdExe.execute(httpServletRequest, request, usernamePasswordLoginCmd);
    }

}

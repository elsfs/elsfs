package org.elsfs.admin.app.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.elsfs.admin.client.dto.clientobject.SysLoginCO;
import org.elsfs.admin.client.dto.login.UsernamePasswordLoginCmd;
import org.elsfs.admin.domain.gateway.LoginGateway;
import org.elsfs.admin.domain.login.AccountUsername;
import org.elsfs.admin.domain.login.EvidencePassword;
import org.elsfs.admin.domain.login.LoginInfo;
import org.elsfs.admin.domain.login.LoginReturnInfo;
import org.elsfs.admin.domain.user.UserInfo;
import org.elsfs.core.dto.SingleResponse;
import org.springframework.stereotype.Component;

/**
 * 用户名密码登陆执行器
 *
 * @author zeng
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class UsernamePasswordLoginCmdExe {

    private final LoginGateway loginGateway;

    public SingleResponse<SysLoginCO> execute(HttpServletRequest request, HttpServletResponse response,
            UsernamePasswordLoginCmd cmd) {
        LoginReturnInfo userInfo = loginGateway.usernamePassword(request, response, new LoginInfo.UsernamePassword(
                new AccountUsername(cmd.getUsername()), new EvidencePassword(cmd.getPassword())));
        return SingleResponse.of(create(userInfo));
    }

    private SysLoginCO create(LoginReturnInfo userInfo) {
        UserInfo info = userInfo.getUserInfo();
        return new SysLoginCO().setUsername(info.getUsername())
            .setAvatar(info.getAvatar())
            .setToken(userInfo.getToken())
            .setRealName(info.getNickname());
    }

}

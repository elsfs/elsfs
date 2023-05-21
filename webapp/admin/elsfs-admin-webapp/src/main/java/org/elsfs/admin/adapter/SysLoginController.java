package org.elsfs.admin.adapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.elsfs.admin.client.api.SysLoginService;
import org.elsfs.admin.client.dto.clientobject.SysLoginCO;
import org.elsfs.admin.client.dto.login.UsernamePasswordLoginCmd;
import org.elsfs.core.dto.SingleResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆控制器
 *
 * @author zeng
 * @since 0.0.1
 */
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService sysLoginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            headers = HttpHeaders.CONTENT_TYPE + "=" + MediaType.APPLICATION_JSON_VALUE)
    public SingleResponse<SysLoginCO> login(HttpServletRequest httpRequest, HttpServletResponse response,
            @RequestBody UsernamePasswordLoginCmd qry) {
        return sysLoginService.usernamePasswordLogin(httpRequest, response, qry);
    }

}

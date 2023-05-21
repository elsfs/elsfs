package org.elsfs.admin.domain.login;

import lombok.Data;
import lombok.experimental.Accessors;
import org.elsfs.admin.domain.user.UserInfo;

/**
 * 登陆返回信息
 *
 * @author zeng
 * @since 0.0.1
 */
@Data
@Accessors(chain = true)
public class LoginReturnInfo {

    private UserInfo userInfo;

    private String token;

}

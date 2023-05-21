package org.elsfs.admin.client.dto.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户名密码登陆
 *
 * @author zeng
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UsernamePasswordLoginCmd extends BasePasswordLoginCmd {

    private String username;

}

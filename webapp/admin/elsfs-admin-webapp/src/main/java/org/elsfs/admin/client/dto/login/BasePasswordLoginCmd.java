package org.elsfs.admin.client.dto.login;

import lombok.Data;
import org.elsfs.core.dto.Command;

/**
 * 密码登陆执行对象
 *
 * @author zeng
 * @since 0.0.1
 */
@Data
public class BasePasswordLoginCmd implements Command {

    private String password;

}

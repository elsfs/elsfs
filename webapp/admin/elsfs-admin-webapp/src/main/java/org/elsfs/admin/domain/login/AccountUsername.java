package org.elsfs.admin.domain.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 账号 用户名
 *
 * @author zeng
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUsername extends Account {

    private String username;

}

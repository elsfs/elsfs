package org.elsfs.admin.domain.login;

import lombok.Getter;
import org.elsfs.core.dto.DTO;

/**
 * 登陆信息
 *
 * @author zeng
 * @since 0.0.1
 */
@Getter
public abstract class LoginInfo<A extends Account, E extends Evidence> implements DTO {

    private final SupportLoginMethods supportLoginMethods;

    private final A account;

    private final E evidence;

    private LoginInfo(SupportLoginMethods supportLoginMethods, A account, E evidence) {
        this.supportLoginMethods = supportLoginMethods;
        this.account = account;
        this.evidence = evidence;
    }

    public static class UsernamePassword extends LoginInfo<AccountUsername, EvidencePassword> {

        public UsernamePassword(AccountUsername account, EvidencePassword evidence) {
            super(SupportLoginMethods.USERNAME_PASSWORD, account, evidence);
        }

    }

}

package org.elsfs.admin.domain.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 支持的登陆方式
 *
 * @author zeng
 * @since 0.0.1
 */
@Getter
@RequiredArgsConstructor
public enum SupportLoginMethods {

    USERNAME_PASSWORD(AccountType.USERNAME, EvidenceType.PASSWORD, "账号密码登陆");

    private final AccountType accountType;

    private final EvidenceType evidenceType;

    private final String intro;

}

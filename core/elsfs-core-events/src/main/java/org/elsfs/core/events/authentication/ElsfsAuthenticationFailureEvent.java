package org.elsfs.core.events.authentication;

import org.elsfs.core.events.AbstractElsfsEvent;

import java.util.Map;

/**
 * 身份验证事务失败事件
 *
 * @author zeng
 * @since 0.0.1
 */
public class ElsfsAuthenticationFailureEvent extends AbstractElsfsEvent {

    private final Map<String, Throwable> failures;

    protected ElsfsAuthenticationFailureEvent(Object source, final Map<String, Throwable> failures) {
        super(source);
        this.failures = failures;
    }

}

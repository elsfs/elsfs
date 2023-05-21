package org.elsfs.core.events.authentication;

import org.elsfs.core.events.AbstractElsfsEvent;

/**
 * 身份验证事务成功事件
 *
 * @author zeng
 * @since 0.0.1
 */
public class ElsfsAuthenticationSuccessEvent extends AbstractElsfsEvent {

    protected ElsfsAuthenticationSuccessEvent(Object source) {
        super(source);
    }

}

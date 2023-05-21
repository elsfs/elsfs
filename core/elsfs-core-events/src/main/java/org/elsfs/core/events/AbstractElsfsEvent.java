package org.elsfs.core.events;

import java.util.EventObject;

/**
 * 抽象事件发布对象
 *
 * @author zeng
 * @since 0.0.1
 */
abstract public class AbstractElsfsEvent extends EventObject {

    protected AbstractElsfsEvent(Object source) {
        super(source);
    }

}

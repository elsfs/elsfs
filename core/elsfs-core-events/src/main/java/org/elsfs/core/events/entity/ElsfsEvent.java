package org.elsfs.core.events.entity;

import java.io.Serializable;
import java.time.Instant;

/**
 * 事件存储对象
 *
 * @author zeng
 * @since 0.0.1
 */
public interface ElsfsEvent extends Serializable {

    String getId();

    String getType();

    String getPrincipalId();

    default Instant creationTime() {
        return Instant.now();
    }

    String getServerIpAddress();

    String getClientIpAddress();

}

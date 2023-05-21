package org.elsfs.core.events;

import org.elsfs.core.events.entity.ElsfsEvent;

/**
 * @author zeng
 * @since 0.0.1
 */
public interface EventRepositoryFilter {

    /**
     * 没有操作事件存储库筛选器。
     * @return 事件存储库筛选器
     *
     */
    static EventRepositoryFilter noOp() {
        return new NoOpEventRepositoryFilter();
    }

    /**
     * 事件存储库是否可以保存/跟踪此事件。
     * @param event the event
     * @return true/false
     */
    default boolean shouldSaveEvent(ElsfsEvent event) {
        return true;
    }

    /**
     * The type No op event repository filter. 类型为空事件存储库筛选器。
     */
    class NoOpEventRepositoryFilter implements EventRepositoryFilter {

    }

}

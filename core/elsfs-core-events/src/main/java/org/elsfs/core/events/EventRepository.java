package org.elsfs.core.events;

import org.elsfs.core.events.entity.ElsfsEvent;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

/**
 * 义事件存储库上的DAO操作。
 *
 * @author zeng
 * @since 0.0.1
 */
public interface EventRepository {

    /**
     * Bean name.
     */
    String BEAN_NAME = "eventRepository";

    /**
     * 获取事件存储库筛选器。
     * @return 获取事件存储库筛选器。
     */
    default EventRepositoryFilter getEventRepositoryFilter() {
        return EventRepositoryFilter.noOp();
    }

    /**
     * 保存
     * @param event the event
     * @throws Exception the exception
     */
    void save(ElsfsEvent event) throws Exception;

    /**
     * 加载集合
     */
    Stream<? extends ElsfsEvent> load();

    /**
     * 加载在给定日期之后创建的事件集合。
     * @param dateTime the date time
     * @return the collection
     */
    Stream<? extends ElsfsEvent> load(ZonedDateTime dateTime);

    /**
     * 获取主体的类型的事件。
     * @param type the type
     * @param principal the principal
     * @return the events of type
     */
    Stream<? extends ElsfsEvent> getEventsOfTypeForPrincipal(String type, String principal);

    /**
     * 获取日期之后主体的类型的事件。
     * @param type the type
     * @param principal the principal
     * @param dateTime the date time
     * @return the events of type
     */
    Stream<? extends ElsfsEvent> getEventsOfTypeForPrincipal(String type, String principal, ZonedDateTime dateTime);

    /**
     * 获取type类型的事件。
     * @param type the type
     * @return the events of type
     */
    Stream<? extends ElsfsEvent> getEventsOfType(String type);

    /**
     * 获取日期之后、类型type的事件。
     * @param type the type
     * @param dateTime the date time
     * @return the events of type
     */
    Stream<? extends ElsfsEvent> getEventsOfType(String type, ZonedDateTime dateTime);

    /**
     * 获取主体id的事件。
     * @param id the id
     * @return the events for principal
     */
    Stream<? extends ElsfsEvent> getEventsForPrincipal(String id);

    /**
     * 获取日期之后主体的事件。
     * @param id the id
     * @param dateTime the date time
     * @return the events for principal
     */
    Stream<? extends ElsfsEvent> getEventsForPrincipal(String id, ZonedDateTime dateTime);

}

package org.elsfs.core.util.logging;

/**
 * @author zeng
 * @since 0.0.1
 */
@FunctionalInterface
public interface LoggingInitialization {

    /**
     * Sets main arguments. 日志参数
     * @param mainArguments the main arguments
     */
    void setMainArguments(String[] mainArguments);

}

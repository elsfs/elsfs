package org.elsfs.util.function;

/**
 * 表示接受单个输入参数但不返回结果的操作.
 *
 * @param <T> 输入类型
 * @author .zeng.
 * @version 0.0.1
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

    /**
     * accept.
     * @param t 输入类型
     * @throws Throwable 异常
     */
    void accept(T t) throws Throwable;

}

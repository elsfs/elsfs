package org.elsfs.util.function;

/**
 * 表示结果的供应商。
 *
 * @param <T> 返回类型
 * @author zeng
 * @since 0.0.1
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

    /**
     * @return t
     * @throws Throwable Throwable
     */
    T get() throws Throwable;

}

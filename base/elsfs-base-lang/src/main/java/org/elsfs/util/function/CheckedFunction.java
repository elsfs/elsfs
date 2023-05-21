package org.elsfs.util.function;

import java.util.function.Function;

/**
 * 表示接受一个参数并生成结果的函数.
 * <p>
 * 这是一个 function接口. 其功能方法是{@link #apply(Object)}.
 *
 * @param <T> 函数的输入类型.
 * @param <R> 函数结果的类型.
 * @author zeng.
 * @since 0.0.1
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {

    /**
     * 将此函数应用于给定的参数.
     * @param t 函数自变量.
     * @return 函数结果.
     * @throws Throwable 产生的异常.
     */
    R apply(T t) throws Throwable;

    /**
     * @param <T> 类型
     * @return 返回一个始终返回其输入参数的函数
     */
    static <T> Function<T, T> identity() {
        return t -> t;
    }

}

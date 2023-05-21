package org.elsfs.security.core.authentication;

import java.util.function.Function;

/**
 * 处理数据映射 返回数据映射
 *
 * @param <C>
 * @param <U>
 */
@FunctionalInterface
public interface InfoMapper<C, U> extends Function<C, U> {

}

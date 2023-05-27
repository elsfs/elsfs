package org.elsfs.security.core.jwt;

/**
 * @param <T> 参数类型
 * @param <C> 参数类型
 */
@FunctionalInterface
public interface JwtGeneratorFactory<T, C> {

    T generate(C context);

}

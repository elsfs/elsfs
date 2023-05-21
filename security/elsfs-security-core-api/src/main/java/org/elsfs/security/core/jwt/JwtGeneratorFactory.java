package org.elsfs.security.core.jwt;

/**
 * @param <T> ����
 * @param <C> ����
 */
@FunctionalInterface
public interface JwtGeneratorFactory<T, C> {

    T generate(C context);

}

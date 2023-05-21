package org.elsfs.util.function;

/**
 * @author zeng
 * @param <T> ����
 */
public interface Filter<T> {

    boolean accept(T entry);

}

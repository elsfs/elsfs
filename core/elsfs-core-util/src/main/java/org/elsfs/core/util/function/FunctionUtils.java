package org.elsfs.core.util.function;

import org.elsfs.util.function.CheckedFunction;
import org.elsfs.util.function.CheckedSupplier;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author zeng
 * @since 0.0.1
 */

public class FunctionUtils {

    public static final Consumer<Throwable> THROWABLE_TO_RUNTIME_EXCEPTION = (t) -> {
        if (t instanceof Error) {
            throw (Error) t;
        }
        else if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        else if (t instanceof IOException) {
            throw new UncheckedIOException((IOException) t);
        }
        else {
            if (t instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

        }
    };

    /**
     * @param <T> ���Ͳ���
     * @param <R> ���Ͳ���
     * @param condition ����
     * @param trueFunction the true function
     * @param falseFunction the false function
     * @return the function
     */
    public static <T, R> Function<T, R> doIf(final Predicate<Object> condition, final Supplier<R> trueFunction,
            final Supplier<R> falseFunction) {
        return t -> {
            try {
                if (condition.test(t)) {
                    return trueFunction.get();
                }
                return falseFunction.get();
            }
            catch (final Throwable e) {
                return falseFunction.get();
            }
        };
    }

    /**
     * Do if consumer.
     * @param <T> ���Ͳ���
     * @param condition ����
     * @param trueFunction the true function
     * @param falseFunction the false function
     * @return the consumer
     */
    public static <T> Consumer<T> doIf(final boolean condition, final Consumer<T> trueFunction,
            final Consumer<T> falseFunction) {
        return account -> {
            if (condition) {
                trueFunction.accept(account);
            }
            else {
                falseFunction.accept(account);
            }
        };
    }

    /**
     * Do and handle supplier.
     * @param <R> ���Ͳ���
     * @param function the function
     * @param errorHandler the error handler
     * @return the supplier
     */
    public static <R> Supplier<R> doAndHandle(final CheckedSupplier<R> function,
            final CheckedFunction<Throwable, R> errorHandler) {
        return () -> {
            try {
                return function.get();
            }
            catch (final Throwable e) {
                try {
                    return errorHandler.apply(e);
                }
                catch (final Throwable ex) {
                    if (ex instanceof RuntimeException) {
                        throw (RuntimeException) ex;
                    }
                    throw new IllegalArgumentException(ex);
                }
            }
        };
    }

    /**
     * Do unchecked.
     * @param <T> ���Ͳ���
     * @param consumer the consumer
     * @return the t
     */
    public static <T> T doUnchecked(final CheckedSupplier<T> consumer) {
        return supplier(consumer, THROWABLE_TO_RUNTIME_EXCEPTION).get();
    }

    /**
     * @param supplier �ṩ��
     * @param handler ����
     * @return Supplier
     * @param <T> ���Ͳ���
     */
    public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.get();
            }
            catch (Throwable var3) {
                handler.accept(var3);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", var3);
            }
        };
    }

}

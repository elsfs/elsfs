package org.elsfs.annotation;

import java.lang.annotation.*;

/**
 * 发布日志
 *
 * @author zeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogPublish {

    boolean async() default true;

    /**
     * 描述
     * @return {String}
     */
    String value() default "";

    /**
     * spel 表达式
     * @return 日志描述
     */
    String expression() default "";

}

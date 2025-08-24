package com.huang.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author Ikaros
 * @since 2025/8/25 00:53 星期一
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptLogger {
    /**
     * @return 操作描述
     */
    String value() default "";

    /**
     * @return 操作模块
     */
    String tag() default "";

    /**
     * @return 描述信息
     */
    String description() default "";
}

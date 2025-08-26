package com.huang.annotation;

/**
 * 访问日志注解
 *
 * @author Ikaros
 * @since 2025/8/26 19:11 星期二
 */
public @interface VisitLogger {
    /**
     * @return 访问页面
     */
    String value() default "";
}

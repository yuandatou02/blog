package com.huang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时区枚举类
 *
 * @author Ikaros
 * @since 2025/8/22 12:12 星期五
 */
@Getter
@AllArgsConstructor
public enum ZoneEnum {
    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");

    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String description;
}

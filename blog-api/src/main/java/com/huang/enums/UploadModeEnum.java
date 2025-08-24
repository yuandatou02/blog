package com.huang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ikaros
 * @since 2025/8/25 01:06 星期一
 */
@Getter
@AllArgsConstructor
public enum UploadModeEnum {
    /**
     * 本地
     */
    LOCAL("local", "localUploadStrategyImpl"),

    /**
     * oss
     */
    OSS("oss", "ossUploadStrategyImpl"),

    /**
     * cos
     */
    COS("cos", "cosUploadStrategyImpl"),

    /**
     * qiniu
     */
    QINIU("qiniu", "qiniuUploadStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 获取策略
     *
     * @param mode 模式
     * @return 搜索策略
     */
    public static String getStrategy(String mode) {
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }
}

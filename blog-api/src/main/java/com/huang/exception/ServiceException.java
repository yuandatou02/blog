package com.huang.exception;

import lombok.Getter;

import static com.huang.enums.StatusCodeEnum.FAIL;

/**
 * 业务异常
 *
 * @author Ikaros
 * @since 2025/8/22 12:10 星期五
 */
@Getter
public class ServiceException extends RuntimeException {
    /**
     * 返回失败状态码
     */
    private final Integer code = FAIL.getCode();

    /**
     * 返回信息
     */
    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }
}

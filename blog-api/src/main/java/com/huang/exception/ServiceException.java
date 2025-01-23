package com.huang.exception;

import lombok.Getter;

import static com.huang.enums.StatusCodeEnum.FAIL;

/**
 * 业务异常
 *
 * @author huang
 * @since 2025年1月23日10:52:24
 **/
@Getter
public final class ServiceException extends RuntimeException {

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

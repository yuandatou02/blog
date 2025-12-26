package com.huang.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author Ikaros
 * @since 2025/12/25 22:19 星期四
 */
@Getter
public class ServiceException extends RuntimeException {

    /**
     * 返回信息
     */
    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }
}

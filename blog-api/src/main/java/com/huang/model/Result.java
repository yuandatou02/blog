package com.huang.model;

import lombok.Data;

/**
 * Result 封装返回结果
 * @author Ikaros
 * @since 2025/8/21 13:11 星期四
 */
@Data
public class Result<T>{
    private Integer code;
    private String msg;
    private T data;

    private Result() {
    }
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(String msg,T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(200, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

}

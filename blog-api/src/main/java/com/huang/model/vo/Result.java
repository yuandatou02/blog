package com.huang.model.vo;

import lombok.Data;

import static com.huang.enums.StatusCodeEnum.FAIL;
import static com.huang.enums.StatusCodeEnum.SUCCESS;

/**
 * 结果返回类
 *
 * @author huang
 * @since 2025年1月20日16:06:52
 */
@Data
public class Result<T> {

    /**
     * 返回状态
     */
    private Boolean flag;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> success(String msg) {
        return buildResult(true, null, SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> success(String msg, T data) {
        return buildResult(true, data, SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> fail(String message) {
        return buildResult(false, null, FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return buildResult(false, null, code, message);
    }

    private static <T> Result<T> buildResult(Boolean flag, T data, Integer code, String message) {
        Result<T> r = new Result<>();
        r.setFlag(flag);
        r.setData(data);
        r.setCode(code);
        r.setMsg(message);
        return r;
    }

}

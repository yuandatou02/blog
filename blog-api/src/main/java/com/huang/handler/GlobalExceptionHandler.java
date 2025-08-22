package com.huang.handler;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.huang.exception.ServiceException;
import com.huang.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.huang.enums.StatusCodeEnum.*;

/**
 * 全局异常处理器
 *
 * @author Ikaros
 * @since 2025/8/22 12:08 星期五
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 处理Assert异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.fail(VALID_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理权限不足
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public Result<?> handleNotPermissionException() {
        return Result.fail("权限不足");
    }

    /**
     * 处理账号封禁
     */
    @ExceptionHandler(value = DisableServiceException.class)
    public Result<?> handleDisableServiceExceptionException() {
        return Result.fail("此账号已被禁止访问服务");
    }

    /**
     * 处理无此角色异常
     */
    @ExceptionHandler(value = NotRoleException.class)
    public Result<?> handleNotRoleException() {
        return Result.fail("权限不足");
    }

    /**
     * 处理SaToken异常
     */
    @ExceptionHandler(value = NotLoginException.class)
    public Result<?> handlerNotLoginException(NotLoginException nle) {
        // 判断场景值，定制化异常信息
        String message = switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN -> "未提供token";
            case NotLoginException.INVALID_TOKEN -> "token无效";
            case NotLoginException.TOKEN_TIMEOUT -> "token已过期";
            default -> "当前会话未登录";
        };
        // 返回给前端
        return Result.fail(UNAUTHORIZED.getCode(), message);
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> handleSystemException(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMsg());
    }
}

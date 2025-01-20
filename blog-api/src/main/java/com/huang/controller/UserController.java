package com.huang.controller;

import com.huang.model.vo.Result;
import com.huang.model.vo.request.LoginReq;
import com.huang.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author huang
 * @since 2025年1月20日15:55:31
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return {@link String} Token
     */
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginReq login) {
        return Result.success("登录成功", userService.login(login));
    }
}

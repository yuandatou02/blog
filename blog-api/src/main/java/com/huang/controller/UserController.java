package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.huang.model.Result;
import com.huang.model.request.LoginReq;
import com.huang.model.response.UserBackInfoResp;
import com.huang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Ikaros
 * @since 2025/12/26 16:44 星期五
 * <p>
 * 用来控制一些账户的操作
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 获取后台登录用户信息
     *
     * @return {@link UserBackInfoResp} 登录用户信息
     */
    @GetMapping("/admin/user/getUserInfo")
    public Result<UserBackInfoResp> getUserBackInfo() {
        return Result.success(userService.getUserBackInfo());
    }

    /**
     * 登录
     *
     * @return token
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginReq loginReq) {
        return Result.success(userService.login(loginReq));
    }


    /**
     * 用户退出
     */
    @SaCheckLogin
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }
}

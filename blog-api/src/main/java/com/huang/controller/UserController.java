package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.huang.model.Result;
import com.huang.model.response.UserBackInfoResp;
import com.huang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Ikaros
 * @since 2025/8/22 17:33 星期五
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
    @SaCheckLogin
    @GetMapping("/admin/user/getUserInfo")
    public Result<UserBackInfoResp> getUserBackInfo() {
        return Result.success("查询用户信息成功", userService.getUserBackInfo());
    }
}

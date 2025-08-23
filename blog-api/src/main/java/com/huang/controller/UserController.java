package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.huang.model.Result;
import com.huang.model.request.PasswordReq;
import com.huang.model.response.RouterResp;
import com.huang.model.response.UserBackInfoResp;
import com.huang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 获取登录用户菜单
     *
     * @return {@link RouterResp} 登录用户菜单
     */
    @SaCheckLogin
    @GetMapping("/admin/user/getUserMenu")
    public Result<List<RouterResp>> getUserMenu() {
        return Result.success("获取菜单成功", userService.getUserMenu());
    }

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

    /**
     * 修改管理员密码
     *
     * @param password 密码
     * @return {@link Result<>}
     */
    @SaCheckRole("1")
    @PutMapping("/admin/password")
    public Result<?> updateAdminPassword(@Validated @RequestBody PasswordReq password) {
        userService.updateAdminPassword(password);
        return Result.success("密码修改成功");
    }
}

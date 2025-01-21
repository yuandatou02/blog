package com.huang.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.huang.model.vo.Result;
import com.huang.model.vo.request.LoginReq;
import com.huang.model.vo.response.RouterResp;
import com.huang.model.vo.response.UserBackInfoResp;
import com.huang.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 获取登录用户菜单
     *
     * @return {@link RouterResp} 登录用户菜单
     */
    @GetMapping("/admin/user/getUserMenu")
    public Result<List<RouterResp>> getUserMenu() {
        return Result.success("获取用户菜单成功", userService.getUserMenu());
    }

    /**
     * 获取后台登录用户信息
     *
     * @return {@link UserBackInfoResp} 登录用户信息
     */
    @GetMapping("/admin/user/getUserInfo")
    public Result<UserBackInfoResp> getUserBackInfo() {
        return Result.success("获取用户信息成功", userService.getUserBackInfo());
    }

    /**
     * 用户退出
     *
     * @return @return {@link String} 退出成功信息
     */
    @GetMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout();
        return Result.success("用户退出成功");
    }

    /**
     * 用户登录
     *
     * @param login 登录参数
     * @return {@link String} Token
     */
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginReq login) {
        return Result.success("用户登录成功", userService.login(login));
    }
}

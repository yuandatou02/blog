package com.huang.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.huang.entity.User;
import com.huang.model.Result;
import com.huang.model.request.LoginRequest;
import com.huang.model.response.LoginResponse;
import com.huang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆控制器
 * @author Ikaros
 * @since 2025/8/21 14:34 星期四
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 登陆接口
     * @param loginRequest 登陆请求
     * @return 登陆响应
     */
    @PostMapping("/admin/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User login = userService.login(loginRequest);
        if (!"ROLE_admin".equals(login.getRole())) {
            return Result.error(403, "无权限");
        }
        StpUtil.login(login.getId());
        return Result.success("登陆成功", LoginResponse.builder().token(StpUtil.getTokenValue()).user(login).build());
    }
}

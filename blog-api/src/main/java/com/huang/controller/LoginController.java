package com.huang.controller;

import com.huang.model.Result;
import com.huang.model.request.LoginRequest;
import com.huang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆控制器
 *
 * @author Ikaros
 * @since 2025/8/21 14:34 星期四
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 登陆接口
     *
     * @param loginRequest 登陆请求
     * @return token 令牌
     */
    @PostMapping("/admin/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        return Result.success("登陆成功", userService.login(loginRequest));
    }
}

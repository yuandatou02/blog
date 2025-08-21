package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.User;
import com.huang.mapper.UserMapper;
import com.huang.model.request.LoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 用户服务类
 * @author Ikaros
 * @since 2025/8/21 14:45 星期四
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    /**
     * 通过用户名和密码获取用户信息
     * @param loginRequest 登录请求参数
     * @return 用户信息
     */
    public User login(LoginRequest loginRequest) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<>(User.class).eq(User::getUsername, loginRequest.getUsername())
                .eq(User::getPassword, SaSecureUtil.sha256(loginRequest.getPassword())));
        Assert.notNull(user, "用户名或密码错误");
        user.setPassword(null);
        return user;
    }
}

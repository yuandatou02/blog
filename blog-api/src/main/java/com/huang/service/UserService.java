package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.User;
import com.huang.mapper.UserMapper;
import com.huang.model.request.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 用户服务
 *
 * @author Ikaros
 * @since 2025/12/26 16:43 星期五
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    /**
     * 登录方法
     *
     * @param loginReq 登录参数
     * @return token
     */
    public String login(LoginReq loginReq) {
        // 查询用户
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginReq.getUsername())
                .eq(User::getPassword, SaSecureUtil.sha256(loginReq.getPassword())));
        Assert.notNull(user, "用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        // 返回token
        return StpUtil.getTokenValue();
    }
}

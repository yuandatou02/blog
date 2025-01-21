package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.User;
import com.huang.mapper.UserMapper;
import com.huang.model.vo.request.LoginReq;
import com.huang.model.vo.response.UserBackInfoResp;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 用户业务服务
 *
 * @author huang
 * @since 2025年1月20日15:49:09
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {


    /**
     * 获取后台登录用户信息
     *
     * @return {@link UserBackInfoResp} 登录用户信息
     */
    public UserBackInfoResp getUserBackInfo() {
        // 获取登录的用户ID
        Integer id = StpUtil.getLoginIdAsInt();
        // 通过ID查询用户信息
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getAvatar)
                .eq(User::getId, id));
        // 查询用户角色
        List<String> roleList = StpUtil.getRoleList();
        // 用户权限列表
        List<String> permissionList = StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .toList();
        return UserBackInfoResp.builder()
                .id(id)
                .avatar(user.getAvatar())
                .roleList(roleList)
                .permissionList(permissionList)
                .build();
    }

    /**
     * 用户登录方法
     *
     * @param login 用户登录信息
     * @return token
     */
    public String login(LoginReq login) {
        // 查询用户和密码，其中密码使用sha256加密
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().select(User::getId)
                .eq(User::getUsername, login.getUsername())
                .eq(User::getPassword, SaSecureUtil.sha256(login.getPassword())));
        // 判断用户是否存在
        Assert.notNull(user, "用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 检验通过之后在登陆
        StpUtil.login(user.getId());
        // 返回token值
        return StpUtil.getTokenValue();
    }
}

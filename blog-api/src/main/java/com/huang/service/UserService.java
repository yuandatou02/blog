package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.User;
import com.huang.mapper.UserMapper;
import com.huang.model.request.LoginReq;
import com.huang.model.response.UserBackInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

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
     * 获取用户信息
     *
     * @return 用户信息
     */
    public UserBackInfoResp getUserBackInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        // 查询用户信息
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar).eq(User::getId, userId));
        // 查询用户角色
        List<String> roleIdList = StpUtil.getRoleList();
        // 用户权限列表
        List<String> permissionList = StpUtil.getPermissionList().stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return UserBackInfoResp.builder()
                .id(userId)
                .avatar(user.getAvatar())
                .roleList(roleIdList)
                .permissionList(permissionList)
                .build();
    }

    /**
     * 登录
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

package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.constants.CommonConstant;
import com.huang.entity.User;
import com.huang.mapper.MenuMapper;
import com.huang.mapper.UserMapper;
import com.huang.model.request.LoginRequest;
import com.huang.model.response.MetaResp;
import com.huang.model.response.RouterResp;
import com.huang.model.response.UserBackInfoResp;
import com.huang.model.response.UserMenuResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户服务类
 *
 * @author Ikaros
 * @since 2025/8/21 14:45 星期四
 */
@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> {

    private final MenuMapper menuMapper;

    /**
     * 递归生成路由
     *
     * @return 路由列表
     */
    public List<RouterResp> getUserMenu() {
        // 查询用户菜单
        List<UserMenuResp> userMenuRespList = menuMapper.selectMenuByUserId(StpUtil.getLoginIdAsInt());
        // 递归生成路由,parentId为0
        return buildRoutes(CommonConstant.PARENT_ID, userMenuRespList);
    }

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
     * 通过用户名和密码获取用户信息
     *
     * @param loginRequest 登录请求参数
     * @return token 信息
     */
    public String login(LoginRequest loginRequest) {
        // 根据用户名和密码查询用户
        User user = getOne(new LambdaQueryWrapper<>(User.class).select(User::getId).eq(User::getUsername, loginRequest.getUsername())
                .eq(User::getPassword, SaSecureUtil.sha256(loginRequest.getPassword())));
        // 断言用户是否存在
        Assert.notNull(user, "用户名或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        // 返回 token 信息
        return StpUtil.getTokenValue();
    }

    /**
     * 递归生成路由列表
     */
    private List<RouterResp> buildRoutes(Integer parentId, List<UserMenuResp> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return Collections.emptyList();
        }

        return menuList.stream()
                .filter(m -> Objects.equals(m.getParentId(), parentId))
                .map(m -> toRouterResp(m, menuList))
                .collect(Collectors.toList());
    }

    /**
     * 将单个菜单转成路由结点
     */
    private RouterResp toRouterResp(UserMenuResp menu, List<UserMenuResp> menuList) {
        RouterResp route = new RouterResp();
        route.setName(menu.getMenuName());
        route.setPath(computePath(menu));
        route.setComponent(computeComponent(menu));
        route.setMeta(buildMeta(menu));

        // 目录需要继续递归
        if (isDirectory(menu)) {
            List<RouterResp> children = buildRoutes(menu.getId(), menuList);
            route.setChildren(children);

            // 目录下无子结点时的兼容处理
            if (CollectionUtils.isEmpty(children)) {
                route.setAlwaysShow(true);
                route.setRedirect("noRedirect");
            }
        }

        // 一级菜单（内嵌 iframe）特殊处理
        if (isTopMenu(menu)) {
            route.setMeta(null);                       // 父结点不保留 meta
            route.setChildren(buildIframeChild(menu)); // 用一个子结点承载真实页面
        }

        return route;
    }

    /* ---------- 语义化判断 ---------- */

    private boolean isDirectory(UserMenuResp m) {
        return CommonConstant.TYPE_DIR.equals(m.getMenuType());
    }

    private boolean isTopMenu(UserMenuResp m) {
        return CommonConstant.PARENT_ID.equals(m.getParentId())
                && CommonConstant.TYPE_MENU.equals(m.getMenuType());
    }

    /* ---------- 构造辅助 ---------- */

    private MetaResp buildMeta(UserMenuResp m) {
        return MetaResp.builder()
                .title(m.getMenuName())
                .icon(m.getIcon())
                .hidden(CommonConstant.TRUE.equals(m.getIsHidden()))
                .build();
    }

    private List<RouterResp> buildIframeChild(UserMenuResp menu) {
        RouterResp child = new RouterResp();
        child.setName(menu.getMenuName());
        child.setPath(menu.getPath());
        child.setComponent(menu.getComponent());
        child.setMeta(buildMeta(menu));
        return Collections.singletonList(child);
    }

    /* ---------- 计算 path ---------- */

    private String computePath(UserMenuResp m) {
        // 一级目录统一加 /
        if (CommonConstant.PARENT_ID.equals(m.getParentId()) && isDirectory(m)) {
            return "/" + m.getPath();
        }
        // 一级菜单直接指向 /
        if (isTopMenu(m)) {
            return "/";
        }
        return m.getPath();
    }

    /* ---------- 计算组件 ---------- */

    private String computeComponent(UserMenuResp m) {
        // 优先使用菜单里配置的组件
        if (StringUtils.isNotBlank(m.getComponent()) && !isTopMenu(m)) {
            return m.getComponent();
        }
        // 二级及以上目录使用 ParentView
        if (!CommonConstant.PARENT_ID.equals(m.getParentId()) && isDirectory(m)) {
            return CommonConstant.PARENT_VIEW;
        }
        // 其余情况默认 Layout
        return CommonConstant.LAYOUT;
    }
}

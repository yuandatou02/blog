package com.huang.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.constant.CommonConstant;
import com.huang.entity.User;
import com.huang.mapper.MenuMapper;
import com.huang.mapper.UserMapper;
import com.huang.model.vo.request.LoginReq;
import com.huang.model.vo.response.MetaResp;
import com.huang.model.vo.response.RouterResp;
import com.huang.model.vo.response.UserBackInfoResp;
import com.huang.model.vo.response.UserMenuResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 用户业务服务
 *
 * @author huang
 * @since 2025年1月20日15:49:09
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {


    @Resource
    private MenuMapper menuMapper;

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(UserMenuResp menu) {
        return !menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(UserMenuResp menu) {
        return menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_MENU.equals(menu.getMenuType());
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(UserMenuResp menu) {
        String component = CommonConstant.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = CommonConstant.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(UserMenuResp menu) {
        String routerPath = menu.getPath();
        // 一级目录
        if (menu.getParentId().equals(CommonConstant.PARENT_ID) && CommonConstant.TYPE_DIR.equals(menu.getMenuType())) {
            routerPath = "/" + menu.getPath();
        }
        // 一级菜单
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 递归生成路由列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return 路由列表
     */
    private List<RouterResp> recurRoutes(Integer parentId, List<UserMenuResp> menuList) {
        List<RouterResp> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouterResp routeVO = new RouterResp();
                    routeVO.setName(menu.getMenuName());
                    routeVO.setPath(getRouterPath(menu));
                    routeVO.setComponent(getComponent(menu));
                    routeVO.setMeta(MetaResp.builder()
                            .title(menu.getMenuName())
                            .icon(menu.getIcon())
                            .hidden(menu.getIsHidden().equals(CommonConstant.TRUE))
                            .build());
                    if (menu.getMenuType().equals(CommonConstant.TYPE_DIR)) {
                        List<RouterResp> children = recurRoutes(menu.getId(), menuList);
                        if (CollectionUtils.isNotEmpty(children)) {
                            routeVO.setAlwaysShow(true);
                            routeVO.setRedirect("noRedirect");
                        }
                        routeVO.setChildren(children);
                    } else if (isMenuFrame(menu)) {
                        routeVO.setMeta(null);
                        List<RouterResp> childrenList = new ArrayList<>();
                        RouterResp children = new RouterResp();
                        children.setName(menu.getMenuName());
                        children.setPath(menu.getPath());
                        children.setComponent(menu.getComponent());
                        children.setMeta(MetaResp.builder()
                                .title(menu.getMenuName())
                                .icon(menu.getIcon())
                                .hidden(menu.getIsHidden().equals(CommonConstant.TRUE))
                                .build());
                        childrenList.add(children);
                        routeVO.setChildren(childrenList);
                    }
                    list.add(routeVO);
                }));
        return list;
    }

    /**
     * 获取用户菜单
     *
     * @return {@link RouterResp} 用户菜单信息
     */
    public List<RouterResp> getUserMenu() {
        // 查询用户菜单
        List<UserMenuResp> userMenuRespList = menuMapper.selectMenuByUserId(StpUtil.getLoginIdAsInt());
        // 递归生成路由,parentId为0
        return recurRoutes(CommonConstant.PARENT_ID, userMenuRespList);
    }


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

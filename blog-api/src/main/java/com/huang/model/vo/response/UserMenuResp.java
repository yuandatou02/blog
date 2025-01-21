package com.huang.model.vo.response;

import lombok.Data;

/**
 * 用户菜单Response
 *
 * @author huang
 * @since 2025年1月21日22:32:54
 **/
@Data
public class UserMenuResp {

    /**
     * 菜单id
     */
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    private String menuType;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 是否隐藏 (0否 1是)
     */
    private Integer isHidden;

}

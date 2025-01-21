package com.huang.model.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单Response
 *
 * @author huang
 * @since 2025年1月21日22:26:32
 **/
@Data
public class MenuResp {

    /**
     * 菜单id
     */
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String menuName;

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
     * 权限标识
     */
    private String perms;

    /**
     * 是否隐藏 (0否 1是)
     */
    private Integer isHidden;

    /**
     * 是否禁用 (0否 1是)
     */
    private Integer isDisable;

    /**
     * 菜单排序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 子菜单列表
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuResp> children;

}

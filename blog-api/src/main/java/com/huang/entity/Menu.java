package com.huang.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 菜单实体类
 *
 * @author Ikaros
 * @since 2025/12/27 13:52 星期六
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    /**
     * 菜单id
     */
    private Integer id;
    /**
     * 父菜单id
     */
    private Integer parentId;
    /**
     * 菜单类型
     */
    private Character menuType;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 是否隐藏
     */
    private Boolean isHidden;
    /**
     * 是否删除
     */
    private Boolean isDelete;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}

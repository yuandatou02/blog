package com.huang.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author Ikaros
 * @since 2025/8/21 14:43 星期四
 */
@Data
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录ip
     */
    private String ipAddress;

    /**
     * 登录地址
     */
    private String ipSource;

    /**
     * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
     */
    private Integer loginType;

    /**
     * 是否禁用 (0否 1是)
     */
    private Integer isDisable;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

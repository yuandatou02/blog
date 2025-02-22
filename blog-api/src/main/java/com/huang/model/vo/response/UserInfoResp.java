package com.huang.model.vo.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * 用户登录信息Response
 *
 * @author huang
 * @since 2025年1月21日17:01:38
 **/
@Data
@Builder
public class UserInfoResp {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 点赞文章集合
     */
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    private Set<Object> commentLikeSet;

    /**
     * 点赞说说集合
     */
    private Set<Object> talkLikeSet;

    /**
     * 登录类型
     */
    private Integer loginType;
}

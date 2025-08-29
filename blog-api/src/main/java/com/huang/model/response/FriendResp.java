package com.huang.model.response;

import lombok.Data;

/**
 * 友链响应类
 *
 * @author Ikaros
 * @since 2025/8/30 00:01 星期六
 */
@Data
public class FriendResp {

    /**
     * 友链id
     */
    private Integer id;

    /**
     * 友链颜色
     */
    private String color;

    /**
     * 友链名称
     */
    private String name;

    /**
     * 友链头像
     */
    private String avatar;

    /**
     * 友链地址
     */
    private String url;

    /**
     * 友链介绍
     */
    private String introduction;

}

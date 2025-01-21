package com.huang.model.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * 路由其他信息Response
 *
 * @author huang
 * @since 2025年1月21日22:29:03
 **/
@Data
@Builder
public class MetaResp {

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

}

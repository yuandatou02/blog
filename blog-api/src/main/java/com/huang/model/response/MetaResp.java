package com.huang.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 路由其他信息Response
 *
 * @author Ikaros
 * @since 2025/12/27 14:29 星期六
 */
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

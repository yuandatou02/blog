package com.huang.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 路由Response
 *
 * @author Ikaros
 * @since 2025/12/27 14:28 星期六
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterResp {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 其他信息
     */
    private MetaResp meta;

    /**
     * 子菜单列表
     */
    private List<RouterResp> children;
}

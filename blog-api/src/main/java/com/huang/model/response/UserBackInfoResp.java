package com.huang.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台登录用户信息Response
 *
 * @author Ikaros
 * @since 2025/8/22 17:36 星期五
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBackInfoResp {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private List<String> roleList;

    /**
     * 权限标识
     */
    private List<String> permissionList;
}

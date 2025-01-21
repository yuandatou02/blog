package com.huang.model.vo.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 后台登录用户信息Response
 *
 * @author huang
 * @since 2025年1月21日17:02:49
 **/
@Data
@Builder
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

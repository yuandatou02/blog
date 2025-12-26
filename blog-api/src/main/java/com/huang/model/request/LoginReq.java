package com.huang.model.request;

import lombok.Data;

/**
 * 登录请求参数
 *
 * @author Ikaros
 * @since 2025/12/26 16:51 星期五
 */
@Data
public class LoginReq {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}

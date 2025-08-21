package com.huang.model.request;

import lombok.Data;

/**
 * 登陆请求参数
 * @author Ikaros
 * @since 2025/8/21 14:47 星期四
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}

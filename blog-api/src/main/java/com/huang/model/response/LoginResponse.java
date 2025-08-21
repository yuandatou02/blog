package com.huang.model.response;

import com.huang.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应类
 * @author Ikaros
 * @since 2025/8/21 15:05 星期四
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private User user;
}

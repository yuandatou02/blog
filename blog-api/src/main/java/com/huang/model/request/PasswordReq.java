package com.huang.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户密码
 *
 * @author Ikaros
 * @since 2025/8/23 10:50 星期六
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReq {
    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @Size(min = 6, message = "新密码不能少于6位")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}

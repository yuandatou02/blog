/**
 * 登录表单数据接口
 * 定义了登录时需要提交的用户凭据结构
 */
export interface LoginForm {
    /**
     * 用户名
     * 用于身份验证的用户标识符
     */
    username: string;

    /**
     * 密码
     * 与用户名匹配的认证密钥
     */
    password: string;
}

/**
 * 登录表单数据接口
 * 定义用户登录时提交的表单数据结构
 */
export interface LoginForm {
    /**
     * 用户名 - 用于身份验证的用户名或邮箱
     */
    username: string;

    /**
     * 密码 - 用户登录密码，需符合安全要求
     */
    password: string;
}


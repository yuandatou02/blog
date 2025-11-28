/**
 * 登录表单接口
 * 定义了登录功能所需的表单数据结构
 */
export interface LoginForm {
    // 用户名，用于登录系统的账号
    username: string;
    // 密码，用于登录系统的密码
    password: string;
}
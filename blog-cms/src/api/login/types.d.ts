/**
 * LoginForm 接口定义了登录表单所需的数据结构。
 *
 * @property {string} username - 用户名，用于标识用户身份。
 * @property {string} password - 密码，用于验证用户身份。
 */
export interface LoginForm {
  username: string;
  password: string;
}

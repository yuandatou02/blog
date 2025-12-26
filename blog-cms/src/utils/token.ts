import Cookies from "js-cookie";

const TokenKey: string = "Token";
// token前缀
export const token_prefix = "Bearer ";

/**
 * 获取存储在Cookie中的令牌
 * @returns 返回存储在Cookie中的令牌值，如果不存在则返回null
 */
export const getToken = () => {
    return Cookies.get(TokenKey);
};


/**
 * 设置用户认证令牌
 * @param token - 用户认证令牌字符串
 * @returns 返回Cookies.set的执行结果
 */
export const setToken = (token: string) => {
    // 项目线上部署可以取消注释
    return Cookies.set(TokenKey, token);
};


/**
 * 移除用户认证令牌
 * 用于清除存储在Cookie中的用户认证信息，实现用户登出功能
 */
export const removeToken = () => {
    // 项目线上部署可以取消注释
    Cookies.remove(TokenKey);
};


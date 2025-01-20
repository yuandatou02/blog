import Cookies from "js-cookie";

const TokenKey: string = 'Token';
// token前缀
export const token_prefix = "Bearer ";

// 获取token
export function getToken() {
  return Cookies.get(TokenKey);
}

// 清除token
export function removeToken() {
  // 项目线上部署可以取消注释
  //return Cookies.remove(TokenKey, { domain: domain });
  return Cookies.remove(TokenKey);
}

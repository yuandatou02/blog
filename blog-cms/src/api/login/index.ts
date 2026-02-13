import type { LoginForm } from "@/api/login/types";
import type { AxiosPromise } from "axios";
import type { Result } from "@/model";
import requests from "@/utils/requests.ts";

/**
 * 登录函数，用于向服务器发送登录请求。
 *
 * @param data - 包含登录表单数据的对象，类型为 LoginForm。
 * @returns string - 登录成功返回的 token。
 */
export const login = (data: LoginForm): AxiosPromise<Result<string>> => {
  // 发送 POST 请求到 '/login' 接口，携带登录表单数据
  return requests({
    url: "/login",
    method: "post",
    data
  });
};

/**
 * 用户退出
 */
export const logout = (): AxiosPromise<Result<null>> => {
  return requests({
    url: "/logout",
    method: "get"
  });
};

import type {AxiosPromise} from "axios";
import request from "@/utils/request.ts";
import type {LoginForm} from "@/api/login/types.ts";
import type {Result} from "@/model";

/**
 * 用户登录
 * @param data 登录信息
 * @returns Token
 */
export function login(data: LoginForm): AxiosPromise<Result<string>> {
  return request({
    url: "/login",
    method: "post",
    data,
  });
}

/**
 * 用户退出
 */
export function logout(): AxiosPromise<Result<null>> {
  return request({
    url: "/logout",
    method: "get",
  });
}

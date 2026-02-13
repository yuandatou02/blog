import requests from "@/utils/requests.ts";
import type { AxiosPromise } from "axios";
import type { Result } from "@/model";
import type { Password, UserInfo } from "@/api/user/types";

/**
 * 获取用户信息
 * @returns 用户信息
 */
export const getUserInfo = (): AxiosPromise<Result<UserInfo>> => {
  return requests({
    url: "/admin/user/getUserInfo",
    method: "get"
  });
};

/**
 * 修改管理员密码
 * @param data 密码
 */
export const updateAdminPassword = (data: Password): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/password",
    method: "put",
    data
  });
};

import requests from "@/utils/requests.ts";
import type { AxiosPromise } from "axios";
import type { Result } from "@/model";
import type { UserInfo } from "@/api/user/types";

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

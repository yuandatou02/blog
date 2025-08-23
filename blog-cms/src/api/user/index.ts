import type {AxiosPromise} from "axios";
import type {Result} from "@/model";
import request from "@/utils/request.ts";
import type {Password, UserInfo} from "@/api/user/types.ts";

/**
 * 获取用户信息
 * @returns 用户信息
 */
export function getUserInfo(): AxiosPromise<Result<UserInfo>> {
    return request({
        url: "/admin/user/getUserInfo",
        method: "get",
    });
}

/**
 * 修改管理员密码
 * @param data 密码
 */
export function updateAdminPassword(data: Password): AxiosPromise<Result<null>> {
    return request({
        url: "/admin/password",
        method: "put",
        data,
    });
}
import type {AxiosPromise} from "axios";
import request from "@/utils/request.ts";
import type {loginRequest} from "@/api/login/types.ts";
import type {Result} from "@/model";

/**
 * 用户登录
 * @param data 登录信息
 * @returns Token
 */
export function login(data: loginRequest): AxiosPromise<Result<string>> {
    return request({
        url: "/login",
        method: "post",
        data,
    });
}
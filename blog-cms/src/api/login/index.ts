import type {LoginForm} from "@/api/login/types";
import type {AxiosPromise} from "axios";
import type {Result} from "@/api";
import request from "@/utils/request.ts";

/**
 * 用户登录函数
 * @param data 登录表单数据，包含用户名、密码等登录信息
 * @returns 返回 token
 */
export const login = (data: LoginForm): AxiosPromise<Result<string>> => {
    return request({
        url: "/login",
        method: "POST",
        data,
    });
};

/**
 * 用户登出函数
 * 发起GET请求到logout接口，用于用户登出操作
 *
 * @returns 登出操作
 */
export const logout = (): AxiosPromise<Result<null>> => {
    return request({
        url: "logout",
        method: "GET",
    });
};


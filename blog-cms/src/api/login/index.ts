import type {LoginForm} from "@/api/login/types";
import request from "@/utils/request.ts";
import type {AxiosResponse} from "axios";
import type {Result} from "@/model";

/**
 * 用户登录函数
 * @param data - 登录表单数据，包含用户名和密码等信息
 * @returns token - 登录成功返回的token
 */
export const login = (data: LoginForm): Promise<AxiosResponse<Result<string>>> => {
    return request.post("/login", data);
};

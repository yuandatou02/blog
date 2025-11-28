import type {LoginForm} from "@/api/login/types";
import type {AxiosPromise} from "axios";
import type {Result} from "@/api";
import requests from "@/utils/request.ts";

/**
 * 登录函数
 * @param data 登录表单数据
 * @returns 返回一个AxiosPromise，解析后为包含字符串的结果对象
 */
export const login = (data: LoginForm): AxiosPromise<Result<string>> => { // 定义login函数，接收LoginForm类型参数，返回AxiosPromise<Result<string>>
    return requests({ // 调用requests函数发起请求
        url: "/login", // 请求URL
        method: "post", // 请求方法
        data // 请求数据
    });
};
import axios, { type AxiosError, type AxiosResponse } from "axios";
import {getToken, token_prefix} from "@/utils/token.ts";

const requests = axios.create({
    baseURL: "/api",
    timeout: 10000,
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
});

requests.interceptors.request.use(
    (config) => {
        // 请求带token
        if (getToken()) {
            config.headers["Authorization"] = token_prefix + getToken();
        }
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

// 配置响应拦截器
requests.interceptors.response.use(
    (response: AxiosResponse) => {
        if (!response.data.flag) {
            ElNotification({
                title: "失败",
                message: response.data.message,
                type: "error"
            });
            // 关键：必须 reject，让调用方 catch 到
            return Promise.reject(response);
        }
        return response;
    },
    (error: AxiosError) => {
        let { message } = error;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substring(message.length - 3) + "异常";
        }
        ElMessage({
            message: message,
            type: "error",
            duration: 5 * 1000
        });
        return Promise.reject(error);
    }
);

export default requests;

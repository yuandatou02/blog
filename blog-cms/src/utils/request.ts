import axios from "axios";
import nProgress from "nprogress";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: "/api",
    timeout: 5000,
    headers: {
        "Content-Type": "application/json;charset=utf-8"
    }
});

// 请求拦截器
request.interceptors.request.use(config => {
    nProgress.start();
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(response => {
    nProgress.done();
    const res = response.data;
    if (res.code !== 200) {
        const msg = res.msg || "请求错误";
        ElMessage.error(msg);
        return Promise.reject(new Error(msg));
    }
    return res;
}, error => {
    nProgress.done();
    ElMessage.error(error.message);
    return Promise.reject(error);
});

export default request;
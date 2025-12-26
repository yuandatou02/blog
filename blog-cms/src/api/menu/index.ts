import {type AxiosPromise} from "axios";
import type {Result} from "@/api";
import type {RouteRecordRaw} from "vue-router";
import request from "@/utils/request.ts";

/**
 * 获取用户菜单
 * 该函数用于请求获取当前用户的菜单权限数据
 * @returns 返回一个AxiosPromise，解析后得到包含路由记录数组的结果对象
 */
export const getUserMenu = (): AxiosPromise<Result<RouteRecordRaw[]>> => {
    return request({
        url: "/admin/user/getUserMenu",
        method: "GET",
    });
};

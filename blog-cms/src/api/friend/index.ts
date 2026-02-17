import requests from "@/utils/requests.ts";
import type { AxiosPromise } from "axios";
import type { Friend, FriendForm, FriendQuery } from "@/api/friend/types";
import type { PageResult, Result } from "@/model";

/**
 * 查看友链列表
 * @param params 查询条件
 * @returns 友链列表
 */
export const getFriendList = (params: FriendQuery): AxiosPromise<Result<PageResult<Friend[]>>> => {
  return requests({
    url: "/admin/friend/list",
    method: "get",
    params
  });
};

/**
 * 删除友链
 * @param data 友链id集合
 */
export const deleteFriend = (data: number[]): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/friend/delete",
    method: "delete",
    data
  });
};

/**
 * 修改友链
 * @param data 友链信息
 */
export const updateFriend = (data: FriendForm): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/friend/update",
    method: "put",
    data
  });
};

/**
 * 添加友链
 * @param data 友链信息
 */
export const addFriend = (data: FriendForm): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/friend/add",
    method: "post",
    data
  });
};

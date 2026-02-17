import type { TagForm, TagQuery } from "@/api/tag/types";
import type { AxiosPromise } from "axios";
import type { PageResult, Result } from "@/model";
import type { Tag } from "element-plus";
import requests from "@/utils/requests.ts";

/**
 * 查看标签列表
 * @param params 查询条件
 * @returns 标签列表
 */
export const getTagList = (params?: TagQuery): AxiosPromise<Result<PageResult<Tag[]>>> => {
  return requests({
    url: "/admin/tag/list",
    method: "get",
    params
  });
};

/**
 * 删除标签
 * @param data 标签id集合
 */
export const deleteTag = (data: number[]): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/tag/delete",
    method: "delete",
    data
  });
};

/**
 * 添加标签
 * @param data 标签信息
 */
export const addTag = (data: TagForm): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/tag/add",
    method: "post",
    data
  });
};

/**
 * 修改标签
 * @param data 标签信息
 */
export const updateTag = (data: TagForm): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/tag/update",
    method: "put",
    data
  });
};

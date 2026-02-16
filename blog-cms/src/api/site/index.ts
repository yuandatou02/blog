import type { SiteConfig } from "@/api/site/types";
import type { Result } from "@/model";
import type { AxiosPromise } from "axios";
import requests from "@/utils/requests.ts";

/**
 * 更新网站配置
 * @param data 网站配置
 */
export const updateSiteConfig = (data: SiteConfig): AxiosPromise<Result<null>> => {
  return requests({
    url: "/admin/site/update",
    method: "put",
    data
  });
};

/**
 * 查看网站配置
 * @returns 网站配置
 */
export const getSiteConfig = (): AxiosPromise<Result<SiteConfig>> => {
  return requests({
    url: "/admin/site/list",
    method: "get"
  });
};

/**
 * 上传网站配置图片
 * @returns 图片链接
 */
export const uploadSiteImg = (data: FormData): AxiosPromise<Result<string>> => {
  return requests({
    url: "/admin/site/upload",
    headers: { "content-type": "multipart/form-data" },
    method: "post",
    data
  });
};

import type { AxiosPromise } from 'axios'
import type { Result } from '@/model'
import type { RouteRecordRaw } from 'vue-router'
import request from '@/utils/request.ts'

/**
 * 获取登录用户菜单
 * @returns 登录用户菜单
 */
export function getUserMenu(): AxiosPromise<Result<RouteRecordRaw[]>> {
  return request({
    url: '/admin/user/getUserMenu',
    method: 'get'
  })
}

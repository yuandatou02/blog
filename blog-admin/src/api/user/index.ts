import type { AxiosPromise } from 'axios'
import type { Result } from '@/model'
import request from '@/utils/request.ts'
import type { UserInfo } from '@/api/user/types.ts'

/**
 * 获取用户信息
 * @returns 用户信息
 */
export function getUserInfo(): AxiosPromise<Result<UserInfo>> {
  return request({
    url: '/admin/user/getUserInfo',
    method: 'get'
  })
}

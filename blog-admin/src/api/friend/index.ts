import request from '@/utils/request.ts'
import type { PageResult, Result } from '@/model'
import type { AxiosPromise } from 'axios'
import type { Friend, FriendForm, FriendQuery } from '@/api/friend/types.ts'

/**
 * 查看友链列表
 * @param params 查询条件
 * @returns 友链列表
 */
export function getFriendList(params: FriendQuery): AxiosPromise<Result<PageResult<Friend[]>>> {
  return request({
    url: '/admin/friend/list',
    method: 'get',
    params
  })
}

/**
 * 删除友链
 * @param data 友链id集合
 */
export function deleteFriend(data: number[]): AxiosPromise<Result<null>> {
  return request({
    url: '/admin/friend/delete',
    method: 'delete',
    data
  })
}

/**
 * 添加友链
 * @param data 友链信息
 */
export function addFriend(data: FriendForm): AxiosPromise<Result<null>> {
  return request({
    url: '/admin/friend/add',
    method: 'post',
    data
  })
}

/**
 * 修改友链
 * @param data 友链信息
 */
export function updateFriend(data: FriendForm): AxiosPromise<Result<null>> {
  return request({
    url: '/admin/friend/update',
    method: 'put',
    data
  })
}


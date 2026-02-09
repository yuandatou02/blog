/**
 * 用户状态接口定义
 *
 * 该接口用于描述用户的状态信息，包括用户的唯一标识、头像、角色列表和权限列表。
 *
 * @property {number | null} id - 用户的唯一标识符，可以为数字或null
 * @property {string} avatar - 用户的头像URL
 * @property {string[]} roleList - 用户的角色列表，包含用户所属的所有角色
 * @property {string[]} permissionList - 用户的权限列表，包含用户拥有的所有权限
 */
export interface UserState {
    id: number | null;
    avatar: string;
    roleList: string[];
    permissionList: string[];
}

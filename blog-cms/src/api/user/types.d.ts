/**
 * 登录用户信息
 */
export interface UserInfo {
    /**
     * 用户id
     */
    id: number;
    /**
     * 头像
     */
    avatar: string;
    /**
     * 角色集合
     */
    roleList: string[];
    /**
     * 权限集合
     */
    permissionList: string[];
}

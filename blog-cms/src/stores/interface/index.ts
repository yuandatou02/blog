/**
 * 用户
 */
export interface UserState {
    /**
     * 用户id
     */
    id: number | null;
    /**
     * 用户头像
     */
    avatar: string;
    /**
     * 角色
     */
    roleList: string[];
    /**
     * 权限
     */
    permissionList: string[];
}

/**
 * 应用
 */
export interface AppState {
    /**
     * 侧边栏是否展开
     */
    isCollapse: boolean;
    /**
     * 响应式
     */
    device: string;
    /**
     * 大小
     */
    size: string;
}
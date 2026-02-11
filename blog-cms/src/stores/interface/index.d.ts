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

/**
 * 应用程序状态接口定义
 *
 * 该接口用于描述应用程序的全局状态。
 *
 * @property {boolean} isCollapse - 表示侧边栏是否折叠的布尔值
 * @property {string} device - 表示当前设备类型的字符串（如 'mobile' 或 'desktop'）
 * @property {string} size - 表示当前屏幕尺寸的字符串（如 'small', 'medium', 'large'）
 */
export interface AppState {
    isCollapse: boolean;
    device: string;
    size: string;
}


/**
 * 设置状态接口定义
 *
 * 该接口用于描述应用程序的设置状态，包括标签页视图、固定头部和侧边栏Logo的显示配置。
 *
 * @property {boolean} tagView - 是否启用标签页视图功能
 * @property {boolean} fixedHeader - 是否固定页面头部
 * @property {boolean} sidebarLogo - 侧边栏是否显示Logo
 */
export interface SettingState {
    tagView: boolean;
    fixedHeader: boolean;
    sidebarLogo: boolean;
}


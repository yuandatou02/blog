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

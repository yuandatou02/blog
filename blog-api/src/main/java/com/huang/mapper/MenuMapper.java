package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Menu;
import com.huang.model.vo.response.UserMenuResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author huang
 * @since 2025年1月21日19:10:11
 **/
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询用户菜单列表
     *
     * @param userId 用户id
     * @return 用户菜单列表
     */
    List<UserMenuResp> selectMenuByUserId(@Param("userId") Integer userId);

    /**
     * 根据角色id查询对应权限
     *
     * @param roleId id
     * @return 权限标识
     */
    List<String> selectPermissionByRoleId(@Param("roleId") String roleId);
}

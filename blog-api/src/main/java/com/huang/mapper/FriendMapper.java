package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Friend;
import com.huang.model.request.PageQuery;
import com.huang.model.response.FriendBackResp;
import com.huang.model.response.FriendResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 友链mapper层
 *
 * @author Ikaros
 * @since 2025/8/29 23:58 星期五
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {


    /**
     * 查看友链后台列表
     *
     * @param friendQuery 友链查询条件
     * @return 友链后台列表
     */
    List<FriendBackResp> selectFriendBackVOList(@Param("param") PageQuery friendQuery);

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendResp> selectFriendVOList();
}

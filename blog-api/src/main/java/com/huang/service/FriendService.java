package com.huang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Friend;
import com.huang.mapper.FriendMapper;
import com.huang.model.PageResult;
import com.huang.model.request.FriendReq;
import com.huang.model.request.PageQuery;
import com.huang.model.response.FriendBackResp;
import com.huang.model.response.FriendResp;
import com.huang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链 Service 实现类
 *
 * @author Ikaros
 * @since 2025/8/29 23:59 星期五
 */
@Service
public class FriendService extends ServiceImpl<FriendMapper, Friend> {

    /**
     * 更新友链
     *
     * @param friend 友链信息
     */
    public void updateFriend(FriendReq friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 更新友链
        baseMapper.updateById(newFriend);
    }

    /**
     * 添加友链
     *
     * @param friend 友链信息
     */
    public void addFriend(FriendReq friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 添加友链
        baseMapper.insert(newFriend);
    }

    /**
     * 查询后台友链列表
     *
     * @param friendQuery 查询条件
     * @return 后台友链列表
     */
    public PageResult<FriendBackResp> listFriendBackVO(PageQuery friendQuery) {
        List<FriendBackResp> friendBackRespList = baseMapper.selectFriendBackVOList(friendQuery);
        if (friendBackRespList.isEmpty()) {
            return new PageResult<>();
        }
        return new PageResult<>(friendBackRespList, friendBackRespList.getFirst().getTotalCount());
    }

    /**
     * 查询友链列表
     *
     * @return 友链列表
     */
    public List<FriendResp> listFriendVO() {
        // 查询友链列表
        return baseMapper.selectFriendVOList();
    }
}

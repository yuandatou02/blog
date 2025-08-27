package com.huang.strategy.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huang.constants.RedisConstant;
import com.huang.entity.Talk;
import com.huang.mapper.TalkMapper;
import com.huang.service.RedisService;
import com.huang.strategy.LikeStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 说说点赞策略实现类
 *
 * @author Ikaros
 * @since 2025/8/27 13:39 星期三
 */
@Service("talkLikeStrategyImpl")
@RequiredArgsConstructor
public class TalkLikeStrategyImpl implements LikeStrategy {

    private final RedisService redisService;

    private final TalkMapper talkMapper;

    @Override
    public void like(Integer talkId) {
        Talk talk = talkMapper.selectOne(new LambdaQueryWrapper<Talk>()
                .select(Talk::getId)
                .eq(Talk::getId, talkId));
        Assert.notNull(talk, "说说不存在");
        // 用户id作为键，说说id作为值，记录用户点赞记录
        String userLikeTalkKey = RedisConstant.USER_TALK_LIKE + StpUtil.getLoginIdAsInt();
        // 判断是否点赞
        if (redisService.hasSetValue(userLikeTalkKey, talkId)) {
            // 取消点赞则删除用户id中的说说id
            redisService.deleteSet(userLikeTalkKey, talkId);
            // 说说点赞量-1
            redisService.decrHash(RedisConstant.TALK_LIKE_COUNT, talkId.toString(), 1L);
        } else {
            // 点赞则在用户id记录说说id
            redisService.setSet(userLikeTalkKey, talkId);
            // 说说点赞量+1
            redisService.incrHash(RedisConstant.TALK_LIKE_COUNT, talkId.toString(), 1L);
        }
    }
}

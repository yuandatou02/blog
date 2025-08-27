package com.huang.strategy.context;

import com.huang.enums.LikeTypeEnum;
import com.huang.strategy.LikeStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Ikaros
 * @since 2025/8/27 13:37 星期三
 */
@Service
@RequiredArgsConstructor
public class LikeStrategyContext {

    private final Map<String, LikeStrategy> likeStrategyMap;

    /**
     * 点赞
     *
     * @param likeType 点赞类型
     * @param typeId   类型id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType, Integer typeId) {
        likeStrategyMap.get(likeType.getStrategy()).like(typeId);
    }
}

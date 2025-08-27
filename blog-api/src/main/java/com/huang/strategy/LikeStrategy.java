package com.huang.strategy;

/**
 * @author Ikaros
 * @since 2025/8/27 13:38 星期三
 */
public interface LikeStrategy {
    /**
     * 点赞
     *
     * @param typeId 类型id
     */
    void like(Integer typeId);
}

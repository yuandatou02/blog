package com.huang.model.response;

import lombok.Data;

/**
 * 评论数量Response
 *
 * @author Ikaros
 * @since 2025/8/27 13:56 星期三
 */
@Data
public class CommentCountResp {
    /**
     * 类型id
     */
    private Integer id;

    /**
     * 评论数量
     */
    private Integer commentCount;
}

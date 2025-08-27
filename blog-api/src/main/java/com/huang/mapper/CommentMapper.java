package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Comment;
import com.huang.model.response.CommentCountResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论mapper接口
 *
 * @author Ikaros
 * @since 2025/8/27 14:00 星期三
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据评论类型id获取评论量
     *
     * @param typeIdList  类型id列表
     * @param commentType 评论类型
     * @return 说说评论量
     */
    List<CommentCountResp> selectCommentCountByTypeId(@Param("typeIdList") List<Integer> typeIdList, @Param("commentType") Integer commentType);
}

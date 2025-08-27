package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Talk;
import com.huang.model.request.TalkQuery;
import com.huang.model.response.TalkBackResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 说说 Mapper
 *
 * @author Ikaros
 * @since 2025/8/27 12:01 星期三
 */
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    /**
     * 查询后台说说列表
     *
     * @param talkQuery 说说查询条件
     * @return 后台说说列表
     */
    List<TalkBackResp> selectBackTalkList(@Param("param") TalkQuery talkQuery);
}

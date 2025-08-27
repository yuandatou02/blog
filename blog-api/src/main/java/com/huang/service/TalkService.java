package com.huang.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Talk;
import com.huang.mapper.TalkMapper;
import com.huang.model.PageResult;
import com.huang.model.request.TalkQuery;
import com.huang.model.response.TalkBackResp;
import com.huang.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 说说服务类
 *
 * @author Ikaros
 * @since 2025/8/27 12:02 星期三
 */
@Service
public class TalkService extends ServiceImpl<TalkMapper, Talk> {

    /**
     * 查询说说列表
     *
     * @param talkQuery 说说查询条件
     * @return 说说列表
     */
    public PageResult<TalkBackResp> listTalkBackVO(TalkQuery talkQuery) {
        // 分页查询说说列表
        List<TalkBackResp> talkBackRespList = baseMapper.selectBackTalkList(talkQuery);
        // 判断
        if (talkBackRespList.isEmpty()) {
            return new PageResult<>();
        }
        talkBackRespList.forEach(item -> {
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkBackRespList, talkBackRespList.getFirst().getTotalCount());
    }
}

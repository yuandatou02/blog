package com.huang.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Talk;
import com.huang.enums.FilePathEnum;
import com.huang.mapper.CommentMapper;
import com.huang.mapper.TalkMapper;
import com.huang.model.PageResult;
import com.huang.model.request.PageQuery;
import com.huang.model.request.TalkQuery;
import com.huang.model.request.TalkReq;
import com.huang.model.response.CommentCountResp;
import com.huang.model.response.TalkBackInfoResp;
import com.huang.model.response.TalkBackResp;
import com.huang.model.response.TalkResp;
import com.huang.strategy.context.UploadStrategyContext;
import com.huang.utils.BeanCopyUtils;
import com.huang.utils.CommonUtils;
import com.huang.utils.HTMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.huang.constants.RedisConstant.TALK_LIKE_COUNT;
import static com.huang.enums.ArticleStatusEnum.PUBLIC;
import static com.huang.enums.CommentTypeEnum.TALK;

/**
 * 说说服务类
 *
 * @author Ikaros
 * @since 2025/8/27 12:02 星期三
 */
@Service
@RequiredArgsConstructor
public class TalkService extends ServiceImpl<TalkMapper, Talk> {

    private final UploadStrategyContext uploadStrategyContext;

    private final BlogFileService blogFileService;

    private final RedisService redisService;

    private final CommentMapper commentMapper;

    /**
     * 查询说说列表
     *
     * @param pageQuery 分页查询条件
     * @return 说说列表
     */
    public PageResult<TalkResp> listTalkVO(PageQuery pageQuery) {
        // 查询说说总量
        Long count = baseMapper.selectCount((new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus())));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkResp> talkRespList = baseMapper.selectTalkList(pageQuery);
        // 查询说说评论量
        List<Integer> talkIdList = talkRespList.stream()
                .map(TalkResp::getId)
                .collect(Collectors.toList());
        List<CommentCountResp> commentCountVOList = commentMapper.selectCommentCountByTypeId(talkIdList, TALK.getType());
        Map<Integer, Integer> commentCountMap = commentCountVOList.stream()
                .collect(Collectors.toMap(CommentCountResp::getId, CommentCountResp::getCommentCount));
        // 查询说说点赞量
        Map<String, Integer> likeCountMap = redisService.getHashAll(TALK_LIKE_COUNT)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(), // Key 转为 String
                        entry -> Integer.parseInt(entry.getValue().toString()) // Value 转为 Integer
                ));
        // 封装说说
        talkRespList.forEach(item -> {
            item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setCommentCount(Optional.ofNullable(commentCountMap.get(item.getId())).orElse(0));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkRespList, count);
    }

    /**
     * 查询说说详情
     *
     * @param talkId 说说id
     * @return 说说详情
     */
    public TalkResp getTalkById(Integer talkId) {
        // 查询说说信息
        TalkResp talkResp = baseMapper.selectTalkById(talkId);
        if (Objects.isNull(talkResp)) {
            return null;
        }
        // 查询说说点赞量
        Integer likeCount = redisService.getHash(TALK_LIKE_COUNT, talkId.toString(), Integer.class);
        talkResp.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        // 转换图片格式
        if (Objects.nonNull(talkResp.getImages())) {
            talkResp.setImgList(CommonUtils.castList(JSON.parseObject(talkResp.getImages(), List.class), String.class));
        }
        return talkResp;
    }

    /**
     * 查询首页说说列表
     *
     * @return 首页说说列表
     */
    public List<String> listTalkHome() {
        // 查询最新5条说说
        List<Talk> talkList = baseMapper.selectList(new LambdaQueryWrapper<Talk>()
                .select(Talk::getTalkContent)
                .eq(Talk::getStatus, PUBLIC.getStatus())
                .orderByDesc(Talk::getIsTop)
                .orderByDesc(Talk::getId)
                .last("limit 5"));
        return talkList.stream()
                .map(item -> item.getTalkContent().length() > 200
                        ? HTMLUtils.deleteHtmlTag(item.getTalkContent().substring(0, 200))
                        : HTMLUtils.deleteHtmlTag(item.getTalkContent()))
                .collect(Collectors.toList());
    }


    /**
     * 查询说说详情
     *
     * @param talkId 说说id
     * @return 说说详情
     */
    public TalkBackInfoResp editTalk(Integer talkId) {
        TalkBackInfoResp talkBackVO = baseMapper.selectTalkBackById(talkId);
        // 转换图片格式
        if (Objects.nonNull(talkBackVO.getImages())) {
            talkBackVO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackVO.getImages(), List.class), String.class));
        }
        return talkBackVO;
    }

    /**
     * 更新说说
     *
     * @param talk 说说信息
     */
    public void updateTalk(TalkReq talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.updateById(newTalk);
    }

    /**
     * 新增说说
     *
     * @param talk 说说信息
     */
    public void addTalk(TalkReq talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.insert(newTalk);
    }

    /**
     * 上传说说封面
     *
     * @param file 说说封面文件
     * @return 上传后的url
     */
    public String uploadTalkCover(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.TALK.getFilePath());
        return url;
    }

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

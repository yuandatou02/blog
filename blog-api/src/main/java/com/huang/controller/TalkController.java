package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.annotation.AccessLimit;
import com.huang.annotation.OptLogger;
import com.huang.annotation.VisitLogger;
import com.huang.enums.LikeTypeEnum;
import com.huang.model.PageResult;
import com.huang.model.Result;
import com.huang.model.request.PageQuery;
import com.huang.model.request.TalkQuery;
import com.huang.model.request.TalkReq;
import com.huang.model.response.TalkBackInfoResp;
import com.huang.model.response.TalkBackResp;
import com.huang.model.response.TalkResp;
import com.huang.service.TalkService;
import com.huang.strategy.context.LikeStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.huang.constants.OptTypeConstant.*;

/**
 * 说说控制器
 *
 * @author Ikaros
 * @since 2025/8/27 11:55 星期三
 */
@RestController
@RequiredArgsConstructor
public class TalkController {

    private final static String TAG = "说说模块";

    private final TalkService talkService;

    private final LikeStrategyContext likeStrategyContext;

    /**
     * 删除说说
     *
     * @param talkId 说说id
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE, tag = TAG, description = "删除说说")
    @SaCheckPermission("web:talk:delete")
    @DeleteMapping("/admin/talk/delete/{talkId}")
    public Result<?> deleteTalk(@PathVariable("talkId") Integer talkId) {
        talkService.removeById(talkId);
        return Result.success("删除说说成功");
    }

    /**
     * 修改说说
     *
     * @param talk 说说信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE, tag = TAG, description = "修改说说")
    @SaCheckPermission("web:talk:update")
    @PutMapping("/admin/talk/update")
    public Result<?> updateTalk(@Validated @RequestBody TalkReq talk) {
        talkService.updateTalk(talk);
        return Result.success("修改说说成功");
    }

    /**
     * 编辑说说
     *
     * @param talkId 说说id
     * @return {@link TalkBackResp} 后台说说
     */
    @SaCheckPermission("web:talk:edit")
    @GetMapping("/admin/talk/edit/{talkId}")
    public Result<TalkBackInfoResp> editTalk(@PathVariable("talkId") Integer talkId) {
        return Result.success("编辑说说成功！", talkService.editTalk(talkId));
    }

    /**
     * 点赞说说
     *
     * @param talkId 说说id
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @AccessLimit(seconds = 60, maxCount = 3)
    @SaCheckPermission("web:talk:like")
    @PostMapping("/talk/{talkId}/like")
    public Result<?> saveTalkLike(@PathVariable("talkId") Integer talkId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.TALK, talkId);
        return Result.success("点赞成功");
    }

    /**
     * 查看首页说说
     *
     * @return {@link Result<String>}
     */
    @GetMapping("/home/talk")
    public Result<List<String>> listTalkHome() {
        return Result.success("查看首页说说成功！", talkService.listTalkHome());
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkResp>}
     */
    @VisitLogger(value = "说说列表")
    @GetMapping("/talk/list")
    public Result<PageResult<TalkResp>> listTalkVO(@Validated PageQuery pageQuery) {
        return Result.success("查看说说列表成功！", talkService.listTalkVO(pageQuery));
    }

    /**
     * 查看说说
     *
     * @param talkId 说说id
     * @return {@link Result<TalkResp>}
     */
    @VisitLogger(value = "说说")
    @GetMapping("/talk/{talkId}")
    public Result<TalkResp> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.success("查看说说成功！", talkService.getTalkById(talkId));
    }

    /**
     * 添加说说
     *
     * @param talk 说说信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD, tag = TAG, description = "添加说说")
    @SaCheckPermission("web:talk:add")
    @PostMapping("/admin/talk/add")
    public Result<?> addTalk(@Validated @RequestBody TalkReq talk) {
        talkService.addTalk(talk);
        return Result.success("添加说说成功");
    }

    /**
     * 上传说说图片
     *
     * @param file 文件
     * @return {@link Result<String>}
     */
    @OptLogger(value = UPLOAD, tag = TAG, description = "上传说说图片")
    @SaCheckPermission("web:talk:upload")
    @PostMapping("/admin/talk/upload")
    public Result<String> uploadTalkCover(@RequestParam("file") MultipartFile file) {
        return Result.success("上传说说图片成功", talkService.uploadTalkCover(file));
    }

    /**
     * 查看后台说说列表
     *
     * @param talkQuery 说说查询条件
     * @return {@link TalkBackResp} 后台说说
     */
    @SaCheckPermission("web:talk:list")
    @GetMapping("/admin/talk/list")
    public Result<PageResult<TalkBackResp>> listTalkBackVO(TalkQuery talkQuery) {
        return Result.success("成功获取说说列表", talkService.listTalkBackVO(talkQuery));
    }
}

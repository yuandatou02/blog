package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.model.PageResult;
import com.huang.model.Result;
import com.huang.model.request.TalkQuery;
import com.huang.model.response.TalkBackResp;
import com.huang.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

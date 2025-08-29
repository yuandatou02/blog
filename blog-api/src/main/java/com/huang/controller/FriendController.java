package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.annotation.OptLogger;
import com.huang.annotation.VisitLogger;
import com.huang.model.PageResult;
import com.huang.model.Result;
import com.huang.model.request.FriendReq;
import com.huang.model.request.PageQuery;
import com.huang.model.response.FriendBackResp;
import com.huang.model.response.FriendResp;
import com.huang.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.huang.constants.OptTypeConstant.*;

/**
 * 友链控制器
 *
 * @author Ikaros
 * @since 2025/8/29 23:56 星期五
 */
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final static String TAG = "友链模块";

    private final FriendService friendService;

    /**
     * 查看友链列表
     *
     * @return {@link Result<FriendResp>} 友链列表
     */
    @VisitLogger(value = "友链")
    @GetMapping("/friend/list")
    public Result<List<FriendResp>> listFriendVO() {
        return Result.success("查看友链列表成功!", friendService.listFriendVO());
    }

    /**
     * 查看友链后台列表
     *
     * @param friendQuery 查询条件
     * @return {@link PageResult<FriendBackResp>} 后台友链列表
     */
    @SaCheckPermission("web:friend:list")
    @GetMapping("/admin/friend/list")
    public Result<PageResult<FriendBackResp>> listFriendBackVO(PageQuery friendQuery) {
        return Result.success("查看友链后台列表成功!", friendService.listFriendBackVO(friendQuery));
    }

    /**
     * 添加友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD, tag = TAG, description = "添加友链")
    @SaCheckPermission("web:friend:add")
    @PostMapping("/admin/friend/add")
    public Result<?> addFriend(@Validated @RequestBody FriendReq friend) {
        friendService.addFriend(friend);
        return Result.success("添加友链成功!");
    }

    /**
     * 删除友链
     *
     * @param friendIdList 友链id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE, tag = TAG, description = "删除友链")
    @SaCheckPermission("web:friend:delete")
    @DeleteMapping("/admin/friend/delete")
    public Result<?> deleteFriend(@RequestBody List<Integer> friendIdList) {
        friendService.removeByIds(friendIdList);
        return Result.success("删除友链成功!");
    }

    /**
     * 修改友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE, tag = TAG, description = "修改友链")
    @SaCheckPermission("web:friend:update")
    @PutMapping("/admin/friend/update")
    public Result<?> updateFriend(@Validated @RequestBody FriendReq friend) {
        friendService.updateFriend(friend);
        return Result.success("修改友链成功!");
    }
}

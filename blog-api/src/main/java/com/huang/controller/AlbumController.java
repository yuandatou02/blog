package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.annotation.OptLogger;
import com.huang.annotation.VisitLogger;
import com.huang.model.PageResult;
import com.huang.model.Result;
import com.huang.model.request.AlbumReq;
import com.huang.model.request.PageQuery;
import com.huang.model.response.AlbumBackResp;
import com.huang.model.response.AlbumResp;
import com.huang.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.huang.constants.OptTypeConstant.*;

/**
 * 相册控制器
 *
 * @author Ikaros
 * @since 2025/8/26 17:26 星期二
 */
@RestController
@RequiredArgsConstructor
public class AlbumController {

    private final static String TAG = "相册模块";

    private final AlbumService albumService;

    /**
     * 添加相册
     *
     * @param album 相册信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD, tag = TAG, description = "添加相册")
    @SaCheckPermission("web:album:add")
    @PostMapping("/admin/album/add")
    public Result<?> addAlbum(@Validated @RequestBody AlbumReq album) {
        albumService.addAlbum(album);
        return Result.success("添加相册成功!");
    }

    /**
     * 删除相册
     *
     * @param albumId 相册id
     * @return {@link Result}
     */
    @OptLogger(value = DELETE, tag = TAG, description = "删除相册")
    @SaCheckPermission("web:album:delete")
    @DeleteMapping("/admin/album/delete/{albumId}")
    public Result<?> deleteAlbum(@PathVariable("albumId") Integer albumId) {
        albumService.deleteAlbum(albumId);
        return Result.success("删除相册成功!");
    }

    /**
     * 修改相册
     *
     * @param album 相册信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE, tag = TAG, description = "修改相册")
    @SaCheckPermission("web:album:update")
    @PutMapping("/admin/album/update")
    public Result<?> updateAlbum(@Validated @RequestBody AlbumReq album) {
        albumService.updateAlbum(album);
        return Result.success("修改相册成功!");
    }

    /**
     * 编辑相册
     *
     * @param albumId 相册id
     * @return {@link Result<AlbumReq>} 相册
     */
    @OptLogger(value = UPDATE, tag = TAG, description = "编辑相册")
    @SaCheckPermission("web:album:edit")
    @GetMapping("/admin/album/edit/{albumId}")
    public Result<AlbumReq> editAlbum(@PathVariable("albumId") Integer albumId) {
        return Result.success("编辑相册", albumService.editAlbum(albumId));
    }

    /**
     * 查看相册列表
     *
     * @return {@link Result<AlbumResp> 相册列表
     */
    @VisitLogger(value = "相册")
    @GetMapping("/album/list")
    public Result<List<AlbumResp>> listAlbumVO() {
        return Result.success("查看相册列表成功!", albumService.listAlbumVO());
    }

    /**
     * 上传相册封面
     *
     * @param file 文件
     * @return {@link Result<String>} 相册封面地址
     */
    @OptLogger(value = UPLOAD, tag = TAG, description = "上传相册封面")
    @SaCheckPermission("web:album:upload")
    @PostMapping("/admin/album/upload")
    public Result<String> uploadAlbumCover(@RequestParam("file") MultipartFile file) {
        return Result.success("上传相册封面成功!", albumService.uploadAlbumCover(file));
    }

    /**
     * 查看后台相册列表
     *
     * @param albumQuery 相册查询条件
     * @return {@link PageResult<AlbumBackResp>} 后台相册列表
     */
    @SaCheckPermission("web:album:list")
    @GetMapping("/admin/album/list")
    public Result<PageResult<AlbumBackResp>> listAlbumBackVO(PageQuery albumQuery) {
        return Result.success("查看后台相册列表成功!", albumService.listAlbumBackVO(albumQuery));
    }
}

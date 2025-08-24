package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.annotation.OptLogger;
import com.huang.model.Result;
import com.huang.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.huang.constants.OptTypeConstant.UPLOAD;

/**
 * 网站配置控制器
 *
 * @author Ikaros
 * @since 2025/8/25 00:52 星期一
 */
@RestController
@RequiredArgsConstructor
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    private static final String TAG = "网站配置模块";

    /**
     * 上传网站配置图片
     *
     * @param file 图片
     * @return {@link Result<String>} 图片路径
     */
    @OptLogger(value = UPLOAD, tag = TAG, description = "上传网站配置图片")
    @SaCheckPermission("web:site:upload")
    @PostMapping("/admin/site/upload")
    public Result<String> uploadSiteImg(@RequestParam("file") MultipartFile file) {
        return Result.success("上传网站配置图片成功!", siteConfigService.uploadSiteImg(file));
    }
}

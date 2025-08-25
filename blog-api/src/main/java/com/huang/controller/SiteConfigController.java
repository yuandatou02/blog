package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.annotation.OptLogger;
import com.huang.entity.SiteConfig;
import com.huang.model.Result;
import com.huang.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.huang.constants.OptTypeConstant.UPDATE;
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
     * 获取网站配置
     *
     * @return {@link Result<SiteConfig>} 网站配置
     */
    @SaCheckPermission("web:site:list")
    @GetMapping("/admin/site/list")
    public Result<SiteConfig> getSiteConfig() {
        return Result.success("获取网站配置成功!", siteConfigService.getSiteConfig());
    }

    /**
     * 更新网站配置
     *
     * @param siteConfig 网站配置
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE, tag = TAG, description = "更新网站配置")
    @SaCheckPermission("web:site:update")
    @PutMapping("/admin/site/update")
    public Result<?> updateSiteConfig(@RequestBody SiteConfig siteConfig) {
        siteConfigService.updateSiteConfig(siteConfig);
        return Result.success("更新网站配置成功!");
    }

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

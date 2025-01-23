package com.huang.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huang.entity.SiteConfig;
import com.huang.model.vo.Result;
import com.huang.service.SiteConfigService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站配置控制器
 *
 * @author huang
 * @since 2025年1月22日19:38:54
 **/
@RestController
public class SiteConfigController {

    @Resource
    private SiteConfigService siteConfigService;

    /**
     * 更新网站配置
     *
     * @param siteConfig 网站配置
     * @return {@link Result<>}
     */
    @SaCheckPermission("web:site:update")
    @PutMapping("/admin/site/update")
    public Result<String> updateSiteConfig(@RequestBody SiteConfig siteConfig) {
        siteConfigService.updateSiteConfig(siteConfig);
        return Result.success("更新网站信息成功");
    }


    /**
     * 获取网站配置
     *
     * @return {@link Result<SiteConfig>} 网站配置
     */
    @SaCheckPermission("web:site:list")
    @GetMapping("/admin/site/list")
    public Result<SiteConfig> getSiteConfig() {
        return Result.success("网站配置信息获取成功", siteConfigService.getSiteConfig());
    }

    /**
     * 上传网站配置图片
     *
     * @param file 图片
     * @return {@link Result<String>} 图片路径
     */
    @SaCheckPermission("web:site:upload")
    @PostMapping("/admin/site/upload")
    public Result<String> uploadSiteImg(@RequestParam("file") MultipartFile file) {
        return Result.success("图片上传成功!", siteConfigService.uploadSiteImg(file));
    }
}

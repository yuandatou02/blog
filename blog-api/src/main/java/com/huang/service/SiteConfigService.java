package com.huang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.constant.RedisConstant;
import com.huang.entity.SiteConfig;
import com.huang.enums.FilePathEnum;
import com.huang.mapper.SiteConfigMapper;
import com.huang.strategy.context.UploadStrategyContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 网站配置服务
 *
 * @author huang
 * @since 2025年1月22日22:24:18
 */
@Service
public class SiteConfigService extends ServiceImpl<SiteConfigMapper, SiteConfig> {

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @Resource
    private BlogFileService blogFileService;

    @Resource
    private RedisService redisService;

    /**
     * 更新网站配置信息
     *
     * @param siteConfig 网站配置信息
     */
    public void updateSiteConfig(SiteConfig siteConfig) {
        baseMapper.updateById(siteConfig);
        redisService.deleteObject(RedisConstant.SITE_SETTING);
    }

    /**
     * 获取网站配置信息
     *
     * @return 网站配置信息
     */
    public SiteConfig getSiteConfig() {
        // 从redis中获取网站配置信息。如果没有则从数据库中获取
        SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING);
        if (Objects.isNull(siteConfig)) {
            // 从数据库中加载
            siteConfig = baseMapper.selectById(1);
            redisService.setObject(RedisConstant.SITE_SETTING, siteConfig);
        }
        return siteConfig;
    }

    /**
     * 上传图片服务方法
     *
     * @param file 上传的文件
     * @return 上传成功的图片路径
     */
    public String uploadSiteImg(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.CONFIG.getFilePath());
        return url;
    }
}

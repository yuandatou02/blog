package com.huang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.constants.RedisConstant;
import com.huang.entity.SiteConfig;
import com.huang.enums.FilePathEnum;
import com.huang.mapper.SiteConfigMapper;
import com.huang.strategy.context.UploadStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 网站配置服务类
 *
 * @author Ikaros
 * @since 2025/8/25 00:59 星期一
 */
@Service
@RequiredArgsConstructor
public class SiteConfigService extends ServiceImpl<SiteConfigMapper, SiteConfig> {

    private final UploadStrategyContext uploadStrategyContext;

    private final BlogFileService blogFileService;

    private final RedisService redisService;

    /**
     * 获取网站配置
     *
     * @return 网站配置
     */
    public SiteConfig getSiteConfig() {
        SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING, SiteConfig.class);
        if (Objects.isNull(siteConfig)) {
            // 从数据库中加载
            siteConfig = baseMapper.selectById(1);
            redisService.setObject(RedisConstant.SITE_SETTING, siteConfig);
        }
        return siteConfig;
    }

    /**
     * 更新网站配置
     *
     * @param siteConfig 网站配置
     */
    public void updateSiteConfig(SiteConfig siteConfig) {
        baseMapper.updateById(siteConfig);
        redisService.deleteObject(RedisConstant.SITE_SETTING);
    }

    /**
     * 上传网站图片
     *
     * @param file 图片文件
     * @return 图片url
     */
    public String uploadSiteImg(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.CONFIG.getFilePath());
        return url;
    }
}

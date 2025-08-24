package com.huang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.SiteConfig;
import com.huang.enums.FilePathEnum;
import com.huang.mapper.SiteConfigMapper;
import com.huang.strategy.context.UploadStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

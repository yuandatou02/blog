package com.huang.strategy.context;

import com.huang.strategy.UploadStrategy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.huang.enums.UploadModeEnum.getStrategy;

/**
 * 上传策略上下文
 *
 * @author huang
 * @since 2025年1月22日22:33:15
 */
@Service
public class UploadStrategyContext {
    // 上传模式
    @Value("${upload.strategy}")
    private String uploadStrategy;

    @Resource
    private Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(uploadStrategy)).uploadFile(file, path);
    }
}

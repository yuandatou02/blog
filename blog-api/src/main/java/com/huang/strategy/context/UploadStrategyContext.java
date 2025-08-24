package com.huang.strategy.context;

import com.huang.strategy.UploadStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.huang.enums.UploadModeEnum.getStrategy;

/**
 * 上传策略上下文
 *
 * @author Ikaros
 * @since 2025/8/25 01:02 星期一
 */
@Service
@RequiredArgsConstructor
public class UploadStrategyContext {
    /**
     * 上传模式
     */
    @Value("${upload.strategy}")
    private String uploadStrategy;

    private final Map<String, UploadStrategy> uploadStrategyMap;

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

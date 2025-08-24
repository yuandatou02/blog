package com.huang.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传策略接口
 *
 * @author Ikaros
 * @since 2025/8/25 01:05 星期一
 */
public interface UploadStrategy {
    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 上传路径
     * @return {@link String} 文件地址
     */
    String uploadFile(MultipartFile file, String path);
}

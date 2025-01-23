package com.huang.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传策略
 *
 * @author huang
 * @since 2025年1月22日22:37:33
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

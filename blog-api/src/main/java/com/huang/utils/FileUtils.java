package com.huang.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 文件md5工具类
 *
 * @author huang
 * @since 2025年1月22日22:41:32
 */
@Slf4j
public class FileUtils {

    /**
     * 获取文件md5值
     *
     * @param inputStream 文件输入流
     * @return {@link String} 文件md5值
     */
    public static String getMd5(InputStream inputStream) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5DigestAsHex(inputStream);
        } catch (Exception e) {
            log.error("get md5 error, {}", e.getMessage());
        }
        return md5;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 创建目录
     *
     * @param file 文件
     * @return 是否创建成功
     */
    public static boolean mkdir(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return false;
        }
        return file.mkdirs();
    }

    /**
     * 删除文件
     *
     * @param src 文件
     */
    public static void deleteFile(File src) throws IOException {
 /*       for (File file : Objects.requireNonNull(src.listFiles())) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteFile(file);
            }
        }
        src.delete();
 */
        // 如果文件则直接删除
        if (src.isFile()) {
            org.apache.commons.io.FileUtils.delete(src);
        } else {
            // 如果是文件夹则删除文件夹
            org.apache.commons.io.FileUtils.deleteDirectory(src);
        }
    }

}


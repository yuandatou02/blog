package com.huang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.BlogFile;
import com.huang.mapper.BlogFileMapper;
import com.huang.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static com.huang.constant.CommonConstant.FALSE;

/**
 * 文件服务
 *
 * @author huang
 * @since 2025年1月23日10:59:26
 */
@Service
public class BlogFileService extends ServiceImpl<BlogFileMapper, BlogFile> {

    /**
     * 保存博客文件
     *
     * @param file     上传的文件
     * @param url      文件网络路径
     * @param filePath 文件存储路径
     */
    public void saveBlogFile(MultipartFile file, String url, String filePath) {
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = baseMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, filePath));
            if (Objects.nonNull(existFile)) {
                return;
            }
            // 保存文件信息
            BlogFile newFile = BlogFile.builder()
                    .fileUrl(url)
                    .fileName(md5)
                    .filePath(filePath)
                    .extendName(extName)
                    .fileSize((int) file.getSize())
                    .isDir(FALSE)
                    .build();
            baseMapper.insert(newFile);
        } catch (IOException e) {
            log.error("saveBlogFile is error, {}");
        }
    }
}

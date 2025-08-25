package com.huang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.BlogFile;
import com.huang.mapper.BlogFileMapper;
import com.huang.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


/**
 * 文件服务
 *
 * @author Ikaros
 * @since 2025/8/25 01:26 星期一
 */
@Slf4j
@Service
public class BlogFileService extends ServiceImpl<BlogFileMapper, BlogFile> {

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
                    .isDir(false)
                    .build();
            baseMapper.insert(newFile);
        } catch (IOException e) {
            log.error("saveBlogFile is error, {}", e.getMessage());
        }
    }
}

package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.BlogFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 文件Mapper
 *
 * @author huang
 * @since 2025年1月23日10:57:42
 */
@Repository
public interface BlogFileMapper extends BaseMapper<BlogFile> {

    /**
     * 查询后台文件列表
     *
     * @param fileQuery 文件条件
     * @return 后台文件列表
     */
    //List<FileResp> selectFileVOList(@Param("param") FileQuery fileQuery);
}

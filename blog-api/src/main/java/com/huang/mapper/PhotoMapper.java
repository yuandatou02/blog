package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Photo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 相片 Mapper
 *
 * @author Ikaros
 * @since 2025/8/26 19:02 星期二
 */
@Mapper
public interface PhotoMapper extends BaseMapper<Photo> {
}

package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author Ikaros
 * @since 2025/12/26 16:39 星期五
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

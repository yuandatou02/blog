package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 * @author Ikaros
 * @since 2025/8/21 14:44 星期四
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

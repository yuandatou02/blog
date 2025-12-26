package com.huang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.User;
import com.huang.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author Ikaros
 * @since 2025/12/26 16:43 星期五
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}

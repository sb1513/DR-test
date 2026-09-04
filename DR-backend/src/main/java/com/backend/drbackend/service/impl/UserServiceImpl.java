package com.backend.drbackend.service.impl;

import com.backend.drbackend.entity.User;
import com.backend.drbackend.mapper.UserMapper;
import com.backend.drbackend.service.UserService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {
}

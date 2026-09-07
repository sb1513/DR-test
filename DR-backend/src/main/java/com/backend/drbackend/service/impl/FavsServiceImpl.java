package com.backend.drbackend.service.impl;

import com.backend.drbackend.entity.Favs;
import com.backend.drbackend.mapper.FavsMapper;
import com.backend.drbackend.service.FavsService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FavsServiceImpl
        extends ServiceImpl<FavsMapper, Favs>
        implements FavsService {
}

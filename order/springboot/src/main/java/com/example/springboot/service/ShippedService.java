package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Shipped;
import com.example.springboot.mapper.ShippedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippedService extends ServiceImpl<ShippedMapper, Shipped> {
    @Autowired
    private ShippedMapper shippedMapper;
}

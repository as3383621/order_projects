package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Food;
import com.example.springboot.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService extends ServiceImpl<FoodMapper, Food> {

    @Autowired
    private FoodMapper foodMapper;

    //新增商品或修改商品信息
    public boolean save_food(Food food) {

        return saveOrUpdate(food); //mybatis-plus提供的方法，id存在则更新数据，id不存在则增加数据

    }



}

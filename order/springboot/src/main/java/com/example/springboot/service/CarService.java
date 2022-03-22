package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Car;
import com.example.springboot.entity.SelectCar;
import com.example.springboot.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService extends ServiceImpl<CarMapper, Car> {
    @Autowired
    private CarMapper carMapper;

    public List<SelectCar> SelectCar(String username,Integer pageNum,Integer pageSize) {
        pageNum = (pageNum-1)*pageSize;
        return carMapper.SelectCar(username,pageNum,pageSize);

    }

    public Integer total(String username) {
        return carMapper.total(username);
    }
}

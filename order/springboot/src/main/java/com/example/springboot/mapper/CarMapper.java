package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Car;
import com.example.springboot.entity.SelectCar;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CarMapper extends BaseMapper<Car> {

    @Select("SELECT sys_car.*,sys_food.FoodName,sys_food.url,sys_food.category,sys_food.price,sys_food.stock from sys_car INNER JOIN sys_food ON sys_car.fid=sys_food.id where sys_car.username=#{username} limit #{pageNum},#{pageSize};")
    List<SelectCar> SelectCar(String username,Integer pageNum,Integer pageSize);

    @Select("SELECT count(*) from sys_car INNER JOIN sys_food ON sys_car.fid=sys_food.id where sys_car.username=#{username}")
    Integer total(String username);
}

package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SelectCar {
    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    @TableField(value = "FoodName")
    private String FoodName;
    private String url;
    private String category;
    private Integer price;
    private Integer number;
    private Integer stock;
    private Integer fid;
}

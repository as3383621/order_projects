package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_food")
public class Food {

    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String url;
    @TableField(value = "FoodName")
    private String FoodName;
    private String category;
    private int price;
    private int stock;
    @TableField(value = "create_time")
    private String time;
}

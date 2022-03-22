package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_shipped")
public class Shipped {
    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    private Integer price;
    private Integer number;
    private String url;
    @TableField(value = "FoodName")
    private String FoodName;
    @TableField(value = "create_time")
    private String time;
    private String state;
    private Integer comment;
    private Integer fid;
}

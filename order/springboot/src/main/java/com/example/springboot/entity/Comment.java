package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_comment")
public class Comment {

    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    private String connect;
    @TableField(value = "FoodName")
    private String FoodName;
    @TableField(value = "create_time")
    private String time;

    public Comment(String username, String connect, String foodName) {
        this.username = username;
        this.connect = connect;
        FoodName = foodName;
    }
}

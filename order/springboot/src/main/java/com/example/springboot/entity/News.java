package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_news")
public class News {
    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    private String title;
    private String content;
    @TableField(value = "create_time")
    private String time;
}

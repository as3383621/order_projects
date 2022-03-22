package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_car")
public class Car {
    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    private Integer number;
    private Integer fid;
    public Car(String username,Integer number,Integer fid) {
        this.username = username;
        this.number = number;
        this.fid = fid;
    }
}

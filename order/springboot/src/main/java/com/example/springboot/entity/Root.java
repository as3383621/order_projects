package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


//lombok插件提供的快捷
@Data   //get和set

@TableName(value = "sys_root") //默认按类名User查找，所以要设置表名
public class Root {

    @TableId(type = IdType.AUTO) //主键
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String authority;
    @TableField(value = "create_time")
    private String time;

}

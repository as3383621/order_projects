package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user") //统一设置路径前缀
public class UserController {

    @Autowired
    private UserService userService;


    //根据用户名查询一个用户，用户名检验
    @GetMapping("/one")
    public Result findAll_user(@RequestParam String username) {

        return userService.one(username);
    }

    //用户增加或修改
    @PostMapping
    public Result save_user(@RequestBody User user) {
        if (userService.save_user(user)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"保存失败");
        }
    }

    //用户注册
    @PostMapping("/register")
    public Result save_register(@RequestBody User user) {
        if (userService.save(user)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"注册失败");
        }
    }


    //用户登入
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"账号或密码不能为空");
        } else {
            return Result.success(userService.login(userDTO));
        }
    }

    //删除用户
    @DeleteMapping("/{id}")
    public Result delete_user(@PathVariable Integer id) {

        if(userService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //批量删除
    //删除用户
    @PostMapping("/del/batch")
    public Result deleteBatch_user(@RequestBody List<Integer> ids) {

        if(userService.removeByIds(ids)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String username,
                           @RequestParam String phone,
                           @RequestParam String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("phone", phone);
        queryWrapper.like("address", address);
        IPage<User> userIPage = userService.page(page, queryWrapper);
        return Result.success(userIPage);
    }
}

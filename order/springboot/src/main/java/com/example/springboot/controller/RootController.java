package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.RootDTO;
import com.example.springboot.entity.Root;
import com.example.springboot.service.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/root") //统一设置路径前缀
public class RootController {

    @Autowired
    private RootService rootService;



    //根据用户名查询一个用户，用户名检验
    @GetMapping("/one")
    public Result findAll_root(@RequestParam String username) {

        return rootService.one(username);
    }

    //用户增加或修改
    @PostMapping
    public Result save_root(@RequestBody Root root) {
        if (rootService.save_root(root)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"保存失败");
        }
    }

    //用户注册
    @PostMapping("/register")
    public Result save_register(@RequestBody Root root) {
        if (rootService.save(root)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"注册失败");
        }
    }

    //用户登入
    @PostMapping("/login")
    public Result login(@RequestBody RootDTO rootDTO) {
        String username = rootDTO.getUsername();
        String password = rootDTO.getPassword();
        //判断是否为空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"账号或密码不能为空");
        } else {
            return Result.success(rootService.login(rootDTO));
        }
    }

    //删除用户
    @DeleteMapping("/{id}")
    public Result delete_root(@PathVariable Integer id) {

        if(rootService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //批量删除
    //删除用户
    @PostMapping("/del/batch")
    public Result deleteBatch_root(@RequestBody List<Integer> ids) {

        if(rootService.removeByIds(ids)) {
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
                           @RequestParam String phone) {
        IPage<Root> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("phone", phone);
        IPage<Root> rootIPage = rootService.page(page, queryWrapper);
        return Result.success(rootIPage);
    }
}


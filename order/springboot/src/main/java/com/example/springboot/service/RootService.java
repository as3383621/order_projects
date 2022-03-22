package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.RootDTO;
import com.example.springboot.entity.Root;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.RootMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootService extends ServiceImpl<RootMapper, Root> {

    @Autowired
    private RootMapper rootMapper;

    //用户名检验
    public Result one(String username){
        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Root one = getOne(queryWrapper);
        if (one != null) {
            throw new ServiceException(Constants.CODE_600, "该用户名已注册");
        } else {
            return Result.success();
        }
    }


    //新增用户或修改用户信息
    public boolean save_root(Root root) {

        return saveOrUpdate(root); //mybatis-plus提供的方法，id存在则更新数据，id不存在则增加数据

    }

    //登入验证
    public RootDTO login(RootDTO rootDTO) {
        String username = rootDTO.getUsername();
        String password = rootDTO.getPassword();
        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password",password);
        Root one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (one != null) {
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            rootDTO.setToken(token);
            rootDTO.setUsername(one.getUsername());
            rootDTO.setPassword(one.getPassword());
            return rootDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }
}

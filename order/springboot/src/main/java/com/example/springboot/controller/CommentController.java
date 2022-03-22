package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.Shipped;
import com.example.springboot.service.CommentService;
import com.example.springboot.service.ShippedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment") //统一设置路径前缀
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ShippedService shippedService;


    //分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String username,
                           @RequestParam String FoodName
                           ) {
        IPage<Comment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("FoodName", FoodName);
        IPage<Comment> CommentIPage = commentService.page(page, queryWrapper);
        return Result.success(CommentIPage);
    }
    @GetMapping("/food")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String FoodName
                            ) {
        IPage<Comment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("FoodName", FoodName);
        IPage<Comment> CommentIPage = commentService.page(page, queryWrapper);
        return Result.success(CommentIPage);
    }
    @GetMapping("/add")
    public Result add(@RequestParam String username,
                      @RequestParam String connect,
                      @RequestParam String FoodName,
                      @RequestParam Integer id
                    ) {
        Comment comment = new Comment(username,connect,FoodName);
        if(commentService.save(comment)) {
            UpdateWrapper<Shipped> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",id).set("comment",0);
            Boolean item = shippedService.update(null,wrapper);
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"评论失败");
        }
    }
}

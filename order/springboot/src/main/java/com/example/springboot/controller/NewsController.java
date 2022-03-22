package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.News;
import com.example.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news") //统一设置路径前缀
public class NewsController {
    @Autowired
    private NewsService newsService;

    //根据id查看新闻内容
    @GetMapping("/minute")
    public Result find_News(@RequestParam Integer id) {
        if(newsService.getById(id) != null) {
            return Result.success(newsService.getById(id));
        } else {
            return Result.error(Constants.CODE_600,"查询失败");
        }
    }

    //新闻增加或修改
    @PostMapping
    public Result save_news(@RequestBody News news) {
        if (newsService.save_news(news)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"保存失败");
        }
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete_news(@PathVariable Integer id) {

        if(newsService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch_news(@RequestBody List<Integer> ids) {

        if(newsService.removeByIds(ids)) {
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
                           @RequestParam String title) {
        IPage<News> page = new Page<>(pageNum, pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("title", title);
        IPage<News> NewsIPage = newsService.page(page, queryWrapper);
        return Result.success(NewsIPage);
    }

    @GetMapping("/list")
    public Result Page(    @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        IPage<News> page = new Page<>(pageNum, pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        IPage<News> NewsIPage = newsService.page(page, queryWrapper);
        return Result.success(NewsIPage);
    }
}

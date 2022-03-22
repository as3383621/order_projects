package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Food;
import com.example.springboot.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food") //统一设置路径前缀
public class FoodController {

    @Autowired
    private FoodService foodService;


    //查找菜品信息
    @GetMapping("/minute")
    public Result minute_food(@RequestParam Integer id) {
        if(foodService.getById(id) != null) {
            return Result.success(foodService.getById(id));
        } else {
            return Result.error(Constants.CODE_600,"查询失败");
        }
    }

    //查找菜品信息
    @GetMapping("/more")
    public Result more_food(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam String category) {
        IPage<Food> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", category);
        IPage<Food> FoodIPage = foodService.page(page, queryWrapper);
        return Result.success(FoodIPage);
    }

    //菜品增加或修改
    @PostMapping
    public Result save_food(@RequestBody Food food) {
        if (foodService.save_food(food)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"保存失败");
        }
    }

    //删除菜品
    @DeleteMapping("/{id}")
    public Result delete_food(@PathVariable Integer id) {

        if(foodService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch_food(@RequestBody List<Integer> ids) {

        if(foodService.removeByIds(ids)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //分页查询 - mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String FoodName,
                           @RequestParam String category) {
        IPage<Food> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("FoodName", FoodName);
        queryWrapper.like("category", category);
        IPage<Food> FoodIPage = foodService.page(page, queryWrapper);
        return Result.success(FoodIPage);
    }

    //推荐菜品
    @GetMapping("/recommend")
    public Result findFood(@RequestParam String category) {
        IPage<Food> page = new Page<>(1, 5);
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("category", category);
        IPage<Food> FoodIPage = foodService.page(page, queryWrapper);
        return Result.success(FoodIPage);
    }

    //搜索
    @GetMapping("/select")
    public Result SelectFood(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam String FoodName) {
        IPage<Food> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("FoodName", FoodName);
        IPage<Food> FoodIPage = foodService.page(page, queryWrapper);
        return Result.success(FoodIPage);
    }
}

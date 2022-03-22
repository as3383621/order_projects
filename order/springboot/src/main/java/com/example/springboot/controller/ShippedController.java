package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Food;
import com.example.springboot.entity.Shipped;
import com.example.springboot.service.FoodService;
import com.example.springboot.service.ShippedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipped") //统一设置路径前缀
public class ShippedController {
    @Autowired
    private ShippedService shippedService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/page")
    public Result page(@RequestParam String username,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        IPage<Shipped> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Shipped> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        IPage<Shipped> ShippedIPage = shippedService.page(page, queryWrapper);
        return Result.success(ShippedIPage);
    }

    //后台查找
    @GetMapping("/findpage")
    public Result page(@RequestParam String username,
                       @RequestParam String FoodName,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam String state) {
        IPage<Shipped> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Shipped> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("FoodName", FoodName);
        queryWrapper.like("state", state);
        IPage<Shipped> ShippedIPage = shippedService.page(page, queryWrapper);
        return Result.success(ShippedIPage);
    }

    //结算
    @PostMapping("/pay")
    public Result pay(@RequestBody List<Shipped> shippedList) {
        Integer n = 0;
        for (Shipped value : shippedList) {
            Integer id = value.getFid();
            Food item = foodService.getById(id);
            Integer stock = item.getStock() - value.getNumber();
            if (stock < 0) {
                n = 1;
            }
        }
        if (n == 0) {
            for (Shipped value : shippedList) {
                Integer id = value.getFid();
                UpdateWrapper<Food> wrapper = new UpdateWrapper<>();
                Food item = foodService.getById(id);
                Integer stock = item.getStock() - value.getNumber();
                wrapper.eq("id", id).set("stock",stock);
                foodService.update(null,wrapper);
                shippedService.save(value);
            }
        } else {
            return Result.error(Constants.CODE_600,"订单中有餐品购买的数量超出库存");
        }
        return Result.success();
    }

    @GetMapping("/meal")
    public Result meal(@RequestParam Integer id) {
        UpdateWrapper<Shipped> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("state","已出餐");
        if(shippedService.update(null,wrapper)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"评论失败");
        }
    }

}

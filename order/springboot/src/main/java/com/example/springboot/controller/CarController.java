package com.example.springboot.controller;

import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Car;
import com.example.springboot.entity.SelectCar;
import com.example.springboot.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car") //统一设置路径前缀
public class CarController {
    @Autowired
    private CarService carService;

    //查询用户购物车
    @GetMapping("/page")
    public Result page(@RequestParam String username,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        List<SelectCar> car = carService.SelectCar(username,pageNum,pageSize);
        if(car != null) {
            return Result.success(car);
        } else {
            return Result.error(Constants.CODE_600,"查询失败");
        }
    }

    //获取total
    @GetMapping("/total")
    public Result total(@RequestParam String username) {
        Integer total = carService.total(username);
        return Result.success(total);
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete_car(@PathVariable Integer id) {
        System.out.println(id);
        if(carService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch_car(@RequestBody List<Integer> ids) {
        if(carService.removeByIds(ids)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"删除失败");
        }
    }

    //加入购物车
    @GetMapping("/insert")
    public Result insert_car(@RequestParam String username,
                             @RequestParam Integer number,
                             @RequestParam Integer fid) {
        Car car = new Car(username,number,fid);
        if (carService.save(car)) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600,"加入购物车失败");
        }
    }


}

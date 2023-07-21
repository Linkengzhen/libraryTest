package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")//网页路径一级路径
public class BorrowController {
    @Autowired
    IBorrowService borrowService;

    //新建用户信息接口
    @PostMapping("/save")//网页二级路径
    public Result save(@RequestBody Borrow borrow){
        borrowService.save(borrow);
        return Result.success();
    }

    @PostMapping("/ReturnBookSave")//网页二级路径
    public Result ReturnBookSave(@RequestBody ReturnBook returnBook){
        borrowService.ReturnBookSave(returnBook);
        return Result.success();
    }

    //更新用户信息接口
    @PutMapping("/update")
    public Result updateById(@RequestBody Borrow borrow){
        borrowService.updateById(borrow);
        return Result.success();
    }
    //获取用户id接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Borrow borrow=borrowService.getById(id);
        return Result.success(borrow);
    }

    //删除用户信息接口
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/ReturnBookDelete/{id}")
    public Result ReturnBookDeleteById(@PathVariable Integer id){
        borrowService.ReturnBookDeleteById(id);
        return Result.success();
    }

    //查询用户信息接口
    @GetMapping("/list")
    public Result list(){
        List<Borrow> borrows=borrowService.list();
        return Result.success(borrows);
    }

    //实现分页接口
    @GetMapping("/page")
    public Result page(BorrowPageRequest borrowPageRequest){
        return Result.success(borrowService.page(borrowPageRequest));
    }

    //还书列表显示接口
    @GetMapping("/ReturnBookPage")
    public Result ReturnBookPage(BorrowPageRequest borrowPageRequest){
        return Result.success(borrowService.ReturnBookPage(borrowPageRequest));
    }
    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(borrowService.getCountByTimeRange(timeRange));
    }
}

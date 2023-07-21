package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")//网页路径一级路径

public class UserController {
    //定义一个抽象接口
    @Autowired
    IUserService userService;

    //新建用户信息接口
    @PostMapping("/save")//网页二级路径
    public Result save(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/account")
    public Result AddAccount(@RequestBody User user){
        userService.handleAccount(user);
        return Result.success();
    }

    //更新用户信息接口
    @PutMapping("/update")
    public Result updateById(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest request){
        userService.changePass(request);
        return Result.success();
    }

    //获取用户id接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User user=userService.getById(id);
        return Result.success(user);
    }

    //删除用户信息接口
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    //查询用户信息接口
    @GetMapping("/list")
    public Result list(){
        List<User> users=userService.list();
       return Result.success(users);
    }

    //实现分页接口
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(userService.page(userPageRequest));
    };
}

package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.AdminPageRequest;
import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.RegisterRequest;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.RegisterDTO;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")//网页路径一级路径

public class AdminController {
    //定义一个抽象接口
    @Autowired
    IAdminService adminService;
    //新建用户信息接口
    @PostMapping("/save")//网页二级路径
    public Result save(@RequestBody Admin admin){
        adminService.save(admin);
        return Result.success();
    }

    //更新用户信息接口
    @PutMapping("/update")
    public Result updateById(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.success();
    }
    
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest request){
        adminService.changePass(request);
        return Result.success();
    }

    //获取用户id接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Admin admin=adminService.getById(id);
        return Result.success(admin);
    }

    //删除用户信息接口
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    //查询用户信息接口
    @GetMapping("/list")
    public Result list(){
        List<Admin> admins=adminService.list();
       return Result.success(admins);
    }

    //实现分页接口
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest){
        return Result.success(adminService.page(adminPageRequest));
    }
}

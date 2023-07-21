package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")//网页路径一级路径
public class LoginController {

    @Autowired
    ILoginService LoginService;

    @PostMapping("/adminLogin")//网页二级路径
    public Result adminLogin(@RequestBody LoginRequest request){
        LoginDTO login=LoginService.adminLogin(request);
        return Result.success(login);
    }

    @PostMapping("/userLogin")//网页二级路径
    public Result userLogin(@RequestBody LoginRequest request){
        LoginDTO login=LoginService.userLogin(request);
        return Result.success(login);
    }
}

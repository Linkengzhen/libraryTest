package com.example.springboot.service;

import com.example.springboot.controller.Request.AdminPageRequest;
import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.RegisterRequest;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.RegisterDTO;
import com.example.springboot.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

//业务处理层的抽象接口
public interface IAdminService {
    List<Admin> list();

   PageInfo<Admin> page(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    void changePass(PasswordRequest request);

    RegisterDTO register(RegisterRequest request);
}

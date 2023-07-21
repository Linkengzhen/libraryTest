package com.example.springboot.service;

import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.UserPageRequest;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
//业务处理层的抽象接口
public interface IUserService {
    List<User> list();

    PageInfo<User> page(UserPageRequest userPageRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);


    void handleAccount(User user);

    void register(User user);

    void changePass(PasswordRequest request);
}

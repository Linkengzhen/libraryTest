package com.example.springboot.service.impl;

import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService implements ILoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AdminMapper adminMapper;
    @Override
    public LoginDTO adminLogin(LoginRequest request) {
        Admin admin=null;
        try{
            admin=adminMapper.getByUsername(request.getUsername());
        }catch (Exception e){
            log.error("根据用户名{} 查询出错",request.getUsername());
            throw new ServiceException("系统错误");
        }
        if(admin==null){
            throw new ServiceException("用户名或密码错误");
        }
        //String securePass=securePass(request.getPassword());
        String Pass=request.getPassword();
            /*if(!securePass.equals(admin.getPassword())){
                throw new ServiceException("用户名或密码错误");
            }*/
        if(!Pass.equals(admin.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        if(!admin.isStatus()){
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO=new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);
        return loginDTO;
    }
    @Override
    public LoginDTO userLogin(LoginRequest request) {
        User user=null;
        try{
            user=userMapper.getByUsername(request.getUsername());
        }catch (Exception e){
            log.error("根据用户名{} 查询出错",request.getUsername());
            throw new ServiceException("系统错误");
        }
        if(user==null){
            throw new ServiceException("用户名或密码错误");
        }
        //String securePass=securePass(request.getPassword());
        String Pass=request.getPassword();
            /*if(!securePass.equals(admin.getPassword())){
                throw new ServiceException("用户名或密码错误");
            }*/
        if(!Pass.equals(user.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO=new LoginDTO();
        BeanUtils.copyProperties(user,loginDTO);
        return loginDTO;
    }
}

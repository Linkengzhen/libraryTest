package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.Request.AdminPageRequest;
import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.RegisterRequest;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.RegisterDTO;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {//实现抽象接口的具体方法

    //创建mapper层的对象
    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS="123456";
    private static final String PASS_SALT="ruanjiangongcheng";

    //查询用户方法
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }
    //分页方法
    @Override
    public PageInfo<Admin> page(AdminPageRequest adminPageRequest) {
        PageHelper.startPage(adminPageRequest.getPageNum(),adminPageRequest.getPageSize());
        List<Admin> admins=adminMapper.listByCondition(adminPageRequest);//条件查询
        return new PageInfo<>(admins);
    }

    //新增用户方法
    @Override
    public void save(Admin admin) {
        //默认密码123456
        //if(StrUtil.isBlank(admin.getPassword())) {
            //admin.setPassword(DEFAULT_PASS);
        //}
        //admin.setPassword(securePass(admin.getPassword()));//设置密码加密
        //admin.setPassword(admin.getPassword());
        try {
            adminMapper.save(admin);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， username:{}", admin.getUsername(), e);
            throw new ServiceException("用户名重复");
        }
    }

    //获取用户ID 实现通过id更新or删除特定用户
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    //根据ID更新用户信息
    @Override
    public void updateById(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    //根据ID删除用户信息
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }


    @Override
    public void changePass(PasswordRequest request) {
        //request.setNewPass(securePass(request.getNewPass()));
        request.setNewPass(request.getNewPass());
        int count=adminMapper.updatePassword(request);
        if(count<=0){
            throw new ServiceException("修改密码失败");
        }
    }

    @Override
    public RegisterDTO register(RegisterRequest request) {
        Admin admin=new Admin();
        try {
            adminMapper.save(admin);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， username:{}", admin.getUsername(), e);
            throw new ServiceException("用户名重复");
        }
        RegisterDTO registerDTO=new RegisterDTO();
        BeanUtils.copyProperties(admin,registerDTO);
        return registerDTO;
    }

    private String securePass(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }
}

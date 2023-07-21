package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {//实现抽象接口的具体方法

    //创建mapper层的对象
    @Autowired
    UserMapper userMapper;

    //查询用户方法
    @Override
    public List<User> list() {
        return userMapper.list();}

    //分页方法
    @Override
    public PageInfo<User> page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(),userPageRequest.getPageSize());
        List<User> users=userMapper.listByCondition(userPageRequest);//条件查询
        return new PageInfo<>(users);
    }

    //新增用户方法
    @Override
    public void save(User user) {
        //Date data=new Date();
        //当作卡号,避免用户重复
        //user.setUsername(DateUtil.format(data,"yyyyMMdd")+Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        try {
            userMapper.save(user);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， username:{}", user.getUsername(), e);
            throw new ServiceException("用户名重复");
        }
    }

    //获取用户ID 实现通过id更新or删除特定用户
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    //根据ID更新用户信息
    @Override
    public void updateById(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    //根据ID删除用户信息
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void handleAccount(User user) {
        Integer score=user.getScore();
        if(score==null){
            return;
        }
        Integer id=user.getId();
        User dbUser=userMapper.getById(id);//数据库数据
        dbUser.setAccount(dbUser.getAccount()+score);
        if(dbUser.getAccount()>=0){
            dbUser.setStatus(true);
        }
        userMapper.updateById(dbUser);
    }

    @Override
    public void register(User user) {
        try {
            userMapper.register(user);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， username:{}", user.getUsername(), e);
            throw new ServiceException("用户名重复");
        }
    }

    @Override
    public void changePass(PasswordRequest request) {
        //request.setNewPass(securePass(request.getNewPass()));
        request.setNewPass(request.getNewPass());
        int count=userMapper.updatePassword(request);
        if(count<=0){
            throw new ServiceException("修改密码失败");
        }
    }
}

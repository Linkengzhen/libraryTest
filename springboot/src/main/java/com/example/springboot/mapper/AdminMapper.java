package com.example.springboot.mapper;

import com.example.springboot.controller.Request.AdminPageRequest;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {//实现SQL语句的抽象接口

    //查询接口
    List<Admin> list();

    //条件查询接口
    List<Admin> listByCondition(AdminPageRequest adminPageRequest);

    //新增接口
    void save(Admin admin);

    //获取ID接口
    Admin getById(Integer id);

    //更新接口
    void updateById(Admin admin);

    //删除接口
    void deleteById(Integer id);

    Admin getByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    int updatePassword(PasswordRequest request);

    Admin getByUsername(String username);
}

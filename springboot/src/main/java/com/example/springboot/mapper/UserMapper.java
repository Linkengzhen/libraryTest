package com.example.springboot.mapper;
import com.example.springboot.controller.Request.PasswordRequest;
import com.example.springboot.controller.Request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {//实现SQL语句的抽象接口

    //查询接口
    List<User> list();

    //条件查询接口
    List<User> listByCondition(UserPageRequest userPageRequest);

    //新增接口
    void save(User user);

    //获取ID接口
    User getById(Integer id);

    //更新接口
    void updateById(User user);

    //删除接口
    void deleteById(Integer id);

    User getByUsername(String userName);
    User getByUsernameAndPassword(@Param("username")String username, @Param("password")String password);

    void register(User user);

    int updatePassword(PasswordRequest request);
}

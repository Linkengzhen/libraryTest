package com.example.springboot.mapper;

import com.example.springboot.controller.Request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {//实现SQL语句的抽象接口

    //查询接口
    List<Category> list();

    //条件查询接口
    List<Category> listByCondition(CategoryPageRequest categoryPageRequest);

    //新增接口
    void save(Category category);

    //获取Id接口
    Category getById(Integer id);

    //更新接口
    void updateById(Category category);

    //删除接口
    void deleteById(Integer id);

}

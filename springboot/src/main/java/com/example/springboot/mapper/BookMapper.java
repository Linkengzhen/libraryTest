package com.example.springboot.mapper;

import com.example.springboot.controller.Request.BookPageRequest;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {//实现SQL语句的抽象接口

    //查询接口
    List<Book> list();

    //条件查询接口
    List<Book> listByCondition(BookPageRequest bookPageRequest);

    //新增接口
    void save(Book book);

    //获取Id接口
    Book getById(Integer id);

    //更新接口
    void updateById(Book book);

    //删除接口
    void deleteById(Integer id);

    Book getByNo(String bookNo);

    void updateNumByNo(String bookNo);

}

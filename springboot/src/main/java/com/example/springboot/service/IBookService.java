package com.example.springboot.service;


import com.example.springboot.controller.Request.BookPageRequest;
import com.example.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {
    List<Book> list();

    PageInfo<Book> page(BookPageRequest bookPageRequest);

    void save(Book book);

    Book getById(Integer id);

    void updateById(Book book);

    void deleteById(Integer id);

}

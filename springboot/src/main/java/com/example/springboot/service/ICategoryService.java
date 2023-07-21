package com.example.springboot.service;


import com.example.springboot.controller.Request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {
    List<Category> list();

    PageInfo<Category> page(CategoryPageRequest categoryPageRequest);

    void save(Category category);

    Category getById(Integer id);

    void updateById(Category category);

    void deleteById(Integer id);

}

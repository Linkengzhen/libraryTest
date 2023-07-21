package com.example.springboot.service.impl;

import com.example.springboot.controller.Request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }
    //分页方法
    @Override
    public PageInfo<Category> page(CategoryPageRequest categoryPageRequest) {
        PageHelper.startPage(categoryPageRequest.getPageNum(),categoryPageRequest.getPageSize());
        //自关联查询 pid和id有关系
        List<Category> categories=categoryMapper.listByCondition(categoryPageRequest);//条件查询
        return new PageInfo<>(categories);
    }
    @Override
    public void save(Category category){
        categoryMapper.save(category);
    }
    @Override
    public Category getById(Integer id){
        return categoryMapper.getById(id);
    }
    @Override
    public void updateById(Category category){
        category.setUpdatetime(LocalDate.now());
        categoryMapper.updateById(category);
    }
    @Override
    public void deleteById(Integer id){
        categoryMapper.deleteById(id);
    }
}

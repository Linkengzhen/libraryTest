package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")//网页路径一级路径
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    //新建用户信息接口
    @PostMapping("/save")//网页二级路径
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.success();
    }

    //更新用户信息接口
    @PutMapping("/update")
    public Result updateById(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success();
    }
    //获取用户id接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category=categoryService.getById(id);
        return Result.success(category);
    }

    //删除用户信息接口
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    //查询用户信息接口
    @GetMapping("/list")
    public Result list(){
        List<Category> categories=categoryService.list();
        return Result.success(categories);
    }

    //实现分页接口
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

    @GetMapping("/tree")
    public Result tree() {
        List<Category> list = categoryService.list();

        // 对list操作即可
        return Result.success(createTree(null, list));   //  null 表示从第一级开始递归
    }

    // 完全递归的方法来实现递归树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) {  // 那这就是第一级节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            } else {
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }
}

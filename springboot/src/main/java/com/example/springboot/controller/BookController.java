package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.Request.BookPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")//网页路径一级路径
public class BookController {
    @Autowired
    IBookService bookService;

    //新建用户信息接口
    @PostMapping("/save")//网页二级路径
    public Result save(@RequestBody Book book){
        bookService.save(book);
        return Result.success();
    }

    //更新用户信息接口
    @PutMapping("/update")
    public Result updateById(@RequestBody Book book){
        bookService.updateById(book);
        return Result.success();
    }
    //获取用户id接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Book book=bookService.getById(id);
        return Result.success(book);
    }

    //删除用户信息接口
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        bookService.deleteById(id);
        return Result.success();
    }

    //查询用户信息接口
    @GetMapping("/list")
    public Result list(){
        List<Book> categories=bookService.list();
        return Result.success(categories);
    }

    //实现分页接口
    @GetMapping("/page")
    public Result page(BookPageRequest bookPageRequest){
        return Result.success(bookService.page(bookPageRequest));
    }
}

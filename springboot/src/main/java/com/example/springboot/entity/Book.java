package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Book {
    private Integer id;//书籍id
    private String bookName;//书籍名称
    private String description;//书籍概述
    private String publishDate;//出版时间
    private String author;//
    private String publisher;//出版社
    private String category;//分类
    private String bookNo;//书籍唯一编号ISBM
    private String cover;//书籍图片
    private Integer score;//图书积分
    private Integer bookNumber;//图书数量
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate createtime;//创建时间
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate updatetime;//更新时间
    private List<String> categories;
}

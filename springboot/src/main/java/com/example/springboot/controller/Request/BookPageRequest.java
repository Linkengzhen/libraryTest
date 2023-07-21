package com.example.springboot.controller.Request;

import lombok.Data;

@Data
public class BookPageRequest extends BaseRequest{
    //Book页面所需要的变量，书籍名称以及书籍编号ISBN
    private String bookName;
    private String bookNo;
}

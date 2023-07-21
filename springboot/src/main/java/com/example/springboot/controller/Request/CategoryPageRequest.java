package com.example.springboot.controller.Request;

import lombok.Data;

@Data
public class CategoryPageRequest extends BaseRequest{
    //Category页面所需的变量
    private String name;
}

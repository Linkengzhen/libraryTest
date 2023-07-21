package com.example.springboot.controller.Request;

import lombok.Data;

@Data
//每个页面需要用到的变量
public class BaseRequest {
    private Integer pageNum=1;//分页当前页数
    private Integer pageSize=10;//一个分页10条数据
}

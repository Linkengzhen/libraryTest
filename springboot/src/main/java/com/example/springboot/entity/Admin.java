package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data//实现set方法和get方法的框架
public class Admin {
    private Integer id;//用户id
    private String username;//用户名
    private String phone;//电话
    private String password;//密码
    private String email;//邮箱
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date createtime;//创建时间
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date updatetime;//更新时间
    private boolean status;//管理员状态
}

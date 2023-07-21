package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class User {
    private Integer id;//用户id
    private String name;//姓名
    private String username;//卡号（避免出现重复用户）
    private String password;//密码
    private Integer age;//年龄
    private String sex;//性别
    private String phone;//电话
    private String email;//邮箱
    private String address;//地址
    private Integer account;//余额
    private Integer score;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date createtime;//创建时间
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date updatetime;//更新时间
    private boolean status;
}

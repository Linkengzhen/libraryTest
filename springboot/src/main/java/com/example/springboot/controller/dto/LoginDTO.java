package com.example.springboot.controller.dto;

import lombok.Data;

@Data
//登录时传输的数据
public class LoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
}

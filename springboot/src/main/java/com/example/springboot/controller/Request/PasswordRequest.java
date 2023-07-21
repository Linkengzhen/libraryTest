package com.example.springboot.controller.Request;

import lombok.Data;

@Data
public class PasswordRequest {
    //修改密码所需要的变量
    private String username;
    private String password;
    private String newPass;
}

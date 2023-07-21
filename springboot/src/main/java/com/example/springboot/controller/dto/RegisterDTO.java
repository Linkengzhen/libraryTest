package com.example.springboot.controller.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
}

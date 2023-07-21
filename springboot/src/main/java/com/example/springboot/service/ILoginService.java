package com.example.springboot.service;

import com.example.springboot.controller.Request.LoginRequest;
import com.example.springboot.controller.dto.LoginDTO;

public interface ILoginService {
    LoginDTO adminLogin(LoginRequest request);

    LoginDTO userLogin(LoginRequest request);
}

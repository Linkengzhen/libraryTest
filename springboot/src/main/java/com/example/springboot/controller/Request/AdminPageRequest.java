package com.example.springboot.controller.Request;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest {
    //admin页面所需要的username，phone，email变量
    private String username;
    private String phone;
    private String email;
}

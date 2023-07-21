package com.example.springboot.controller.Request;

import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest {
    //用户页面所需要的初始数据name以及phone
    private String name;
    private String phone;

}

package com.example.springboot.common;

import lombok.Data;

@Data//set和get方法的框架
public class Result {//返回前端提示信息方法的类
    private static final String SUCCESS_CODE="200";
    private static final String ERROR_CODE="-1";

    private String code;//判断是否成功实现编码（标识符）
    private Object data;//返回前端的数据
    private String msg;//返回前端的提示信息


    //返回成功的提示信息
    public static Result success(){
        Result result=new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }
    //返回数据方法
    public static Result success(Object data){
        Result result=new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    //返回错误时提示的信息
    public static Result error(String msg){
        Result result=new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }
}

package com.example.springboot.exception;

public class ServiceException extends RuntimeException{
    //反馈业务层错误
    private String code;
    public String getCode(){
        return code;
    }
    public ServiceException(String message,Throwable cause){
        super(message,cause);
    }
    public ServiceException(String message) {
        super(message);
    }
}

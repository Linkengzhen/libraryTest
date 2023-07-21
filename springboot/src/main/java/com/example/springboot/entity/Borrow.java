package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Borrow {
    private Integer id;
    private String bookName;
    private String bookNo;
    private String userName;
    private String name;
    private String userPhone;
    private Integer score;
    private String status;
    private Integer days;
    private String note;//状态提醒还书
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate returnDate;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate createtime;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate updatetime;
}

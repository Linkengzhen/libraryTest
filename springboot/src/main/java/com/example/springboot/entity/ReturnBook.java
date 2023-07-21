package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnBook {
    private Integer id;
    private String bookName;
    private String bookNo;
    private String userName;
    private String name;
    private String userPhone;
    private Integer score;
    private String status;
    private Integer days;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate borrowDate;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate returnDate;
    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private LocalDate realDate;
}

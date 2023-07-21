package com.example.springboot.service;

import com.example.springboot.controller.Request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

//业务处理层的抽象接口
public interface IBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BorrowPageRequest borrowPageRequest);

    PageInfo<ReturnBook> ReturnBookPage(BorrowPageRequest borrowPageRequest);

    void save(Borrow borrow);

    Borrow getById(Integer id);

    void updateById(Borrow borrow);

    void deleteById(Integer id);

    void ReturnBookSave(ReturnBook returnBook);

    void ReturnBookDeleteById(Integer id);

    Map<String,Object> getCountByTimeRange(String timeRange);
}

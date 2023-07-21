package com.example.springboot.mapper;

import com.example.springboot.controller.Request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.example.springboot.mapper.po.BorrowReturCountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowMapper {//实现SQL语句的抽象接口

    //查询接口
    List<Borrow> list();

    //条件查询接口
    List<Borrow> listByCondition(BorrowPageRequest borrowPageRequest);

    List<ReturnBook> listReturnBookByCondition(BorrowPageRequest borrowPageRequest);

    //新增接口
    void save(Borrow borrow);

    void ReturnBookSave(ReturnBook returnBook);

    //获取Id接口
    Borrow getById(Integer id);

    //更新接口
    void updateById(Borrow borrow);

    //删除接口
    void deleteById(Integer id);

    void ReturnBookDeleteById(Integer id);

    void updateStatus(String status,Integer id);

    List<BorrowReturCountPO> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);
}

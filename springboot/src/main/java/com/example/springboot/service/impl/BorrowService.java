package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.Request.BorrowPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.mapper.po.BorrowReturCountPO;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class BorrowService implements IBorrowService {
    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;
    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }
    //分页方法
    @Override
    public PageInfo<Borrow> page(BorrowPageRequest borrowPageRequest) {
        PageHelper.startPage(borrowPageRequest.getPageNum(),borrowPageRequest.getPageSize());
        List<Borrow> borrows=borrowMapper.listByCondition(borrowPageRequest);
        //计算归还日期
        for(Borrow borrow:borrows){
            LocalDate returnDate=borrow.getReturnDate();
            LocalDate now=LocalDate.now();
            if(now.plusDays(1).isEqual(returnDate)){
                borrow.setNote("即将到期");
            }else if(now.isEqual(returnDate)){
                borrow.setNote("已到期");
            }else if(now.isAfter(returnDate)){
                borrow.setNote("已过期");
            }else{
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }
    public PageInfo<ReturnBook> ReturnBookPage(BorrowPageRequest borrowPageRequest){
        PageHelper.startPage(borrowPageRequest.getPageNum(),borrowPageRequest.getPageSize());
        List<ReturnBook> returnBooks=borrowMapper.listReturnBookByCondition(borrowPageRequest);
        return new PageInfo<>(returnBooks);
    }
    @Override
    public void save(Borrow borrow){
        //1:校验用户积分是否足够
        String userName= borrow.getUserName();
        User user=userMapper.getByUsername(userName);
        if(Objects.isNull(user)){
            throw new ServiceException("用户不存在");
        }
        //2:校验图书数量是否足够
        Book book=bookMapper.getByNo(borrow.getBookNo());
        if(Objects.isNull(book)){
            throw new ServiceException("图书不存在");
        }
        //3:校验图书数量是否足够
        if(book.getBookNumber()<1){
            throw new ServiceException("图书数量不足");
        }
        Integer account=user.getAccount();
        Integer score=book.getScore()*borrow.getDays();//score=借书积分*借书天数
        //4:校验用户积分是否足够
        if(account<score){
            throw new ServiceException("用户借书积分不足");
        }
        //5:更新积分余额
        user.setAccount(user.getAccount()-score);
        userMapper.updateById(user);
        //6:更新图书数量
        book.setBookNumber(book.getBookNumber()-1);
        bookMapper.updateById(book);
        //计算归还天数 当前日期+days=归还日期
        borrow.setReturnDate(LocalDate.now().plus(borrow.getDays(), ChronoUnit.DAYS));
        borrow.setScore(score);
        //7:新增借书记录
        borrowMapper.save(borrow);
    }
    public void ReturnBookSave(ReturnBook returnBook) {
        //改状态
        returnBook.setStatus("已归还");
        borrowMapper.updateStatus("已归还", returnBook.getId());
        //returnBook.setId(null);
        returnBook.setBorrowDate(returnBook.getReturnDate().minusDays(returnBook.getDays()));
        returnBook.setRealDate(LocalDate.now());
        borrowMapper.ReturnBookSave(returnBook);
        bookMapper.updateNumByNo(returnBook.getBookNo());
        Book book = bookMapper.getByNo(returnBook.getBookNo());
        if (book != null) {
            long until=0;
            if (returnBook.getRealDate().isBefore(returnBook.getReturnDate())) {//在归还的日期之前归还，返还积分。
                until = returnBook.getRealDate().until(returnBook.getReturnDate(), ChronoUnit.DAYS);

                //把积分还给用户
            } else if (returnBook.getRealDate().isAfter(returnBook.getReturnDate())) {//逾期归还，扣除额外积分
                until = -returnBook.getReturnDate().until(returnBook.getRealDate(), ChronoUnit.DAYS);

            }
            int score = (int) until * book.getScore();//或取代归还的积分，给用户加/减积分。
            User user=userMapper.getByUsername(returnBook.getUserName());
            int account=user.getAccount()+score;
            user.setAccount(account);
            if(account<0){//若为扣除积分，则获取用户名判断是否不够积分扣除，并进行判断是否锁定用户
                    //锁定用户
                    user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    @Override
    public Borrow getById(Integer id){
        return borrowMapper.getById(id);
    }
    @Override
    public void updateById(Borrow borrow){
        borrow.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(borrow);
    }
    @Override
    public void deleteById(Integer id){
        borrowMapper.deleteById(id);
    }

    @Override
    public void ReturnBookDeleteById(Integer id){
        borrowMapper.ReturnBookDeleteById(id);
    }

    @Override
    public Map<String,Object> getCountByTimeRange(String timeRange) {
        Map<String,Object> map=new HashMap<>();
        Date today=new Date();
        List<DateTime> dateRange;
        switch (timeRange){
            case "week":
                dateRange= DateUtil.rangeToList(DateUtil.offsetDay(today,-6),today, DateField.DAY_OF_WEEK);
                break;
            case "month1":
                dateRange= DateUtil.rangeToList(DateUtil.offsetDay(today,-29),today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange= DateUtil.rangeToList(DateUtil.offsetDay(today,-59),today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange= DateUtil.rangeToList(DateUtil.offsetDay(today,-89),today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange=new ArrayList<>();
        }
        //datetimeToDateStr()是一个处理方法,把DateTime类型的List转化String类型的List。
        List<String> dateStrRange=datetimeToDateStr(dateRange);
        map.put("date",dateStrRange);// x轴的日期数据生成完毕

        //timeRange 借书周期单位，若当日没有借书数据，则不会返回。
        List<BorrowReturCountPO> borrowCount=borrowMapper.getCountByTimeRange(timeRange,1);//y轴的借还的数据生成完毕retrun/borrow
        List<BorrowReturCountPO> returnCount=borrowMapper.getCountByTimeRange(timeRange,2);
        map.put("borrow",countList(borrowCount,dateStrRange));
        map.put("retur",countList(returnCount,dateStrRange));
        return map;
    }
    private List<String> datetimeToDateStr(List<DateTime> dateTimeList){
        List<String> list= CollUtil.newArrayList();
        if(CollUtil.isEmpty(dateTimeList)){
            return list;
        }
        for(DateTime dateTime:dateTimeList){
            String date=DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    //对数据库未统计的时间进行处理
    private List<Integer> countList(List<BorrowReturCountPO> countPOList,List<String> dateRange){
        List<Integer> list=CollUtil.newArrayList();
        if(CollUtil.isEmpty(countPOList)){
            return list;
        }
        for(String date:dateRange){
            //map(BorrowReturCountPO::getCount)取出对应日期的count值
            Integer count=countPOList.stream().filter(countPO -> date.equals(countPO.getDate())).
                    map(BorrowReturCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }
}

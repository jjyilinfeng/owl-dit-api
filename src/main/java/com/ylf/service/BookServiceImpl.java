package com.ylf.service;

import com.ylf.dao.BookMapper;
import com.ylf.pojo.User;
import com.ylf.util.Functions;

import java.util.List;

/**
 * @author Master 2021/2/7
 *
 */
public class BookServiceImpl  implements BookService{

    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }


    @Override
    public List<User> checkLogin(String userName, String password) {
        return bookMapper.checkLogin(userName,password);
    }

    @Override
    public int updateUserInf(User user) {
        return bookMapper.updateUserInf(user);
    }

    @Override
    public User queryUserInfoByName(String userName) {
        return bookMapper.queryUserInfoByName(userName);
    }

    @Override
    public Integer checkRename(String userName) {
        return bookMapper.checkRename(userName);
    }

    @Override
    public Integer checkReEmail(String userEmail) {
        return bookMapper.checkReEmail(userEmail);
    }

    @Override
    public int insertUser(User user) {
        return bookMapper.insertUser(user);
    }

    @Override
    public void initUserInfo(String userName) {
        bookMapper.initUserInfo(userName);
    }

    @Override
    public void insertUserIp(String userNameForIp, String userIp) {
        bookMapper.insertUserIp(userNameForIp,userIp);
    }

    @Override
    public void sendEmail(String checkCode, String email) {
        Functions.sendEmail(checkCode,email);
    }
}

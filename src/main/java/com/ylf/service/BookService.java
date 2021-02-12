package com.ylf.service;

import com.ylf.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Master 2021/2/7
 *
 */
public interface BookService {
    /**
     * 登录判断
     * @param userName 用户名
     * @param userPassword 密码
     * @return 查询到的账户
     */
    List<User> checkLogin(String userName, String userPassword);

    /**
     * 更新用户安全信息
     * @param user 用户
     * @return 是否成功更新
     */
    int updateUserInf(User user);

    /**
     * 根据用户名查询用户数据
     * @param userName 用户名
     * @return 用户数据
     */
    User queryUserInfoByName(String userName);

    /**
     * 检查是否重名
     * @param userName 用户名
     * @return 用户
     */
    Integer checkRename(String userName);

    /**
     * 检查是否重邮箱
     * @param userEmail 用户名
     * @return 用户ID
     */
    Integer checkReEmail(String userEmail);

    /**
     * 插入一个用户
     * @param user 用户信息
     * @return 改变的数值
     */
    int insertUser(User user);

    /**
     * 插入一个用户信息
     * @param userName 用户名
     */
    void initUserInfo(@Param("userName") String userName);

    /**
     * 插入一个用户IP
     * @param userNameForIp 用户名
     * @param userIp IP
     */
    void insertUserIp(String userNameForIp, String userIp);


    /**
     * 发送一封带有验证码的邮件
     * @param checkCode 验证码
     * @param email 邮箱
     */
    void sendEmail(String checkCode, String email);
}

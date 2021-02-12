package com.ylf.dao;

import com.ylf.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 77415
 */
public interface BookMapper {

    /**
     * 登录判断
     * @param userName 用户名
     * @param userPassword 密码
     * @return 查询到的账户
     */
    List<User> checkLogin(@Param("userName")String userName, @Param("userPassword")String userPassword);

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
     * @return 用户ID
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
     * @return 是否成功
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
    void insertUserIp(@Param("userNameForIp")String userNameForIp, @Param("userIp")String userIp);
}

package com.ylf.dao;

import com.ylf.pojo.User;
import com.ylf.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Master 2021/2/10
 */
public interface IndexMapper {

    /**
     * 查询所有用户
     * @return 返回所用用户的数值
     */
    List<User> queryAllUser();

    /**
     * 查询用户个人信息
     * @param userName 用户名
     * @return 用户信息
     */
    UserInfo queryUserInfoByUserName(@Param("userName") String userName);

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return 改变的数据条数
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 更新用户头像
     * @param userName 用户名
     * @param userFaceUuid 用户头像文件
     * @return 改变的数据条数
     */
    int updateUserFace(@Param("userName") String userName,@Param("userFaceUuid") String userFaceUuid);

}

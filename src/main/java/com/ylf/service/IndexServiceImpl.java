package com.ylf.service;

import com.ylf.dao.IndexMapper;
import com.ylf.pojo.User;
import com.ylf.pojo.UserInfo;

import java.util.List;

/**
 * @author Master 2021/2/10
 */
public class IndexServiceImpl implements IndexService{
    IndexMapper indexMapper;

    public void setIndexMapper(IndexMapper indexMapper) {
        this.indexMapper = indexMapper;
    }

    @Override
    public List<User> queryAllUser() {
        return indexMapper.queryAllUser();
    }

    @Override
    public UserInfo queryUserInfoByUserName(String userName) {
        return indexMapper.queryUserInfoByUserName(userName);
    }

    @Override
    public int updateUserFace(String userName, String userFaceUuid) {
        return indexMapper.updateUserFace(userName,userFaceUuid);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return indexMapper.updateUserInfo(userInfo);
    }
}

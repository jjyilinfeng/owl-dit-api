package com.ylf.service;

import com.ylf.dao.IndexMapper;
import com.ylf.pojo.Dit;
import com.ylf.pojo.User;
import com.ylf.pojo.UserIndex;
import com.ylf.pojo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Master 2021/2/10
 */
public class IndexServiceImpl implements IndexService{
    IndexMapper indexMapper;

    public void setIndexMapper(IndexMapper indexMapper) {
        this.indexMapper = indexMapper;
    }

    @Override
    public int insertFollow(String concernUserName, String concernByUserName) {
        return indexMapper.insertFollow(concernUserName,concernByUserName);
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
    public int queryUserConcernCountByUserName(String userName) {
        return indexMapper.queryUserConcernCountByUserName(userName);
    }

    @Override
    public int queryUserFanCountByUserName(String userName) {
        return indexMapper.queryUserFanCountByUserName(userName);
    }

    @Override
    public int queryUserDitsByUserName(String userName) {
        return indexMapper.queryUserDitsByUserName(userName);
    }

    @Override
    public int queryUserConcernWhether(String userConcernName, String userByConcernName) {
        return indexMapper.queryUserConcernWhether(userConcernName,userByConcernName);
    }

    @Override
    public List<Map<String, Object>> querySearchKeyWord() {
        return indexMapper.querySearchKeyWord();
    }

    @Override
    public List<UserInfo> searchUser(String searchWords) {
        return indexMapper.searchUser(searchWords);
    }

    @Override
    public List<Map<String, Object>> searchDit(String searchWords) {
        return indexMapper.searchDit(searchWords);
    }

    @Override
    public List<User> searchArticle(String searchWords) {
        return indexMapper.searchArticle(searchWords);
    }

    @Override
    public int queryPraiseCountByUserName(String userName) {
        return indexMapper.queryPraiseCountByUserName(userName);
    }

    @Override
    public int queryArticleCountByUserName(String userName) {
        return indexMapper.queryArticleCountByUserName(userName);
    }


    @Override
    public int updateUserFace(String userName, String userFaceUuid) {
        return indexMapper.updateUserFace(userName,userFaceUuid);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return indexMapper.updateUserInfo(userInfo);
    }

    @Override
    public List<Map<String, Object>> searchKeyWordRetrieval(String searchWord) {
        return indexMapper.searchKeyWordRetrieval(searchWord);
    }

    @Override
    public int insertSearchWord(String searchWord) {
        return indexMapper.insertSearchWord(searchWord);
    }

    @Override
    public int updateSearchWord(String searchId) {
        return indexMapper.updateSearchWord(searchId);
    }

    @Override
    public int deleteFollow(String concernUserName, String concernByUserName) {
        return indexMapper.deleteFollow(concernUserName,concernByUserName);
    }

    @Override
    public List<Map<String, Object>> queryUserConcernInfoByUserName(String userName) {
        return indexMapper.queryUserConcernInfoByUserName(userName);
    }

    @Override
    public List<Map<String, Object>> queryUserByConcernInfoByUserName(String userName) {
        return indexMapper.queryUserByConcernInfoByUserName(userName);
    }

    @Override
    public List<Map<String, Object>> queryUserIndexInfoByUserName(String userName) {
        return indexMapper.queryUserIndexInfoByUserName(userName);
    }

    @Override
    public List<Map<String, Object>> queryWeekDitInfoByUserName(String userName) {
        return indexMapper.queryWeekDitInfoByUserName(userName);
    }

    @Override
    public int updateUserIndexImg(String userName, String uuid) {
        return indexMapper.updateUserIndexImg(userName,uuid);
    }

}

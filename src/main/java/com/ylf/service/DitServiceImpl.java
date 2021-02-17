package com.ylf.service;

import com.ylf.dao.DitMapper;
import com.ylf.pojo.Dit;

import java.util.List;
import java.util.Map;

/**
 * @author Master 2021/2/12
 */
public class DitServiceImpl implements  DitService{
    private DitMapper ditMapper;

    public void setDitMapper(DitMapper ditMapper) {
        this.ditMapper = ditMapper;
    }

    @Override
    public int insertDit(Dit dit) {
        return ditMapper.insertDit(dit);
    }

    @Override
    public List<Map<String, Object>> queryAllDit(String userName, int limit) {
        return ditMapper.queryAllDit(userName,limit);
    }

    @Override
    public List<Map<String, Object>> querySelfDit(String userName, int limit) {
        return ditMapper.querySelfDit(userName,limit);
    }


    @Override
    public int queryAllDitCount(String userName) {
        return ditMapper.queryAllDitCount(userName);
    }

    @Override
    public int querySelfDitCount(String userName) {
        return ditMapper.querySelfDitCount(userName);
    }

    @Override
    public List<Map<String, String>> queryDitImgByDitUuid(String ditUuid) {
        return ditMapper.queryDitImgByDitUuid(ditUuid);
    }

    @Override
    public List<Map<String, Object>> queryDitPraiseByUuid(String ditUuid) {
        return ditMapper.queryDitPraiseByUuid(ditUuid);
    }

    @Override
    public List<Map<String, Object>> queryDitCommentByUuid(String ditUuid) {
        return ditMapper.queryDitCommentByUuid(ditUuid);
    }

    @Override
    public List<Map<String, Object>> queryDitPraiseUnNoticeByUserName(String userName) {
        return ditMapper.queryDitPraiseUnNoticeByUserName(userName);
    }

    @Override
    public List<Map<String, Object>> queryDitCommentUnNoticeByUserName(String userName) {
        return ditMapper.queryDitCommentUnNoticeByUserName(userName);
    }

    @Override
    public int deleteDitComment(String ditCommentUserName, String ditUuid) {
        return ditMapper.deleteDitComment(ditCommentUserName,ditUuid);
    }

    @Override
    public int deleteDitPraise(String ditPraiseUserName, String ditUuid) {
        return ditMapper.deleteDitPraise(ditPraiseUserName,ditUuid);
    }

    @Override
    public int deleteDitByUuid(String ditUuid) {
        return ditMapper.deleteDitByUuid(ditUuid);
    }

    @Override
    public int insertDitPraise(String ditPraiseUserName, String ditUuid) {
        return ditMapper.insertDitPraise(ditPraiseUserName,ditUuid);
    }

    @Override
    public int insertDitComment(String ditCommentUserName, String ditCommentMessage, String ditUuid) {
        return ditMapper.insertDitComment(ditCommentUserName,ditCommentMessage,ditUuid);
    }

    @Override
    public int insertDitImg(Map<String, Object> insertImg) {
        return ditMapper.insertDitImg(insertImg);
    }

    @Override
    public int deleteDitCommentById(int ditCommentId) {
        return ditMapper.deleteDitCommentById(ditCommentId);
    }
}

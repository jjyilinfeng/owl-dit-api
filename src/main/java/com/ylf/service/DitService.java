package com.ylf.service;

import com.ylf.pojo.Dit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Master 2021/2/12
 */
public interface DitService {
    /**
     * 加入一条Dit
     * @param dit dit数据
     * @return 改变的数据条数
     */
    int insertDit(Dit dit);

    /**
     * 查询所有关注的人的DIT
     * @param userName 用户名
     * @param limit 分页的值
     * @return 查询信息
     */
    List<Map<String, Object>> queryAllDit(String userName,int limit);

    /**
     * 查询所有自己的DIT
     * @param userName 用户名
     * @param limit 分页的值
     * @return 查询信息
     */
    List<Map<String, Object>> querySelfDit(@Param("userName")String userName,@Param("limit")int limit);

    /**
     * 查询所有关注的人的DIT的条数
     * @param userName 用户名
     * @return 查询信息
     */
    int queryAllDitCount(String userName);

    /**
     * 查询自己的DIT的条数
     * @param userName 用户名
     * @return 查询信息
     */
    int querySelfDitCount(@Param("userName")String userName);

    /**
     * 根据ditUuid找到这个Dit的所有图片的Uuid
     * @param ditUuid ditUuid
     * @return 图片的Uuid
     */
    List<Map<String, String>> queryDitImgByDitUuid(String ditUuid);

    /**
     * 根据ditUuid找到这个Dit的所有点赞
     * @param ditUuid ditUuid
     * @return 点赞数据
     */
    List<Map<String, Object>> queryDitPraiseByUuid(@Param("ditUuid")String ditUuid);

    /**
     * 根据ditUuid找到这个Dit的所有评论
     * @param ditUuid ditUuid
     * @return 评论数据
     */
    List<Map<String, Object>> queryDitCommentByUuid(@Param("ditUuid")String ditUuid);

    /**
     * 搜索所有未读的赞
     * @param userName 用户名
     * @return 搜索的值
     */
    List<Map<String, Object>> queryDitPraiseUnNoticeByUserName(@Param("userName")String userName);

    /**
     * 搜索所有未读的评论
     * @param userName 用户名
     * @return 搜索的值
     */
    List<Map<String, Object>> queryDitCommentUnNoticeByUserName(@Param("userName")String userName);

    /**
     * 删除DIt评论
     * @param ditCommentUserName 评论的用户名
     * @param ditUuid ditUuid
     * @return 改变的条数
     */
    int deleteDitComment(String ditCommentUserName, String ditUuid);

    /**
     * 删除Dit点赞
     * @param ditPraiseUserName 点赞的用户名
     * @param ditUuid ditUuid
     * @return 改变的条数
     */
    int deleteDitPraise(String ditPraiseUserName,String ditUuid);

    /**
     * 根据ditUuid删除tb_dit,tb_dit_comment,tb_dit_img下的Dit
     * @param ditUuid DitUuid
     * @return 改变的条数
     */
    int deleteDitByUuid(String ditUuid);

    /**
     * 为dit插入一条点赞数据
     * @param ditPraiseUserName 点赞人
     * @param ditUuid ditUuid
     * @return 改变的条数
     */
    int insertDitPraise(@Param("ditPraiseUserName")String ditPraiseUserName,@Param("ditUuid")String ditUuid);

    /**
     * 为dit插入一条评论数据
     * @param ditCommentUserName 评论人
     * @param ditCommentMessage 评论信息
     * @param ditUuid ditUuid
     * @return 改变的条数
     */
    int insertDitComment(@Param("ditCommentUserName")String ditCommentUserName,@Param("ditCommentMessage")String ditCommentMessage,@Param("ditUuid")String ditUuid);

    /**
     * 存入一个Dit的图片
     * @param insertImg 存入图片的信息
     * @return 改变的条数
     */
    int insertDitImg(Map<String, Object> insertImg);

    /**
     * 根据评论Id删除评论
     * @param ditCommentId 评论Id
     * @return 改变的条数
     */
    int deleteDitCommentById(@Param("ditCommentId")int ditCommentId);

}

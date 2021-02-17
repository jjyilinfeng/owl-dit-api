package com.ylf.dao;

import com.ylf.pojo.Dit;
import com.ylf.pojo.User;
import com.ylf.pojo.UserIndex;
import com.ylf.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Master 2021/2/10
 */
public interface IndexMapper {

    /**
     * 加入关键词
     * @param searchWord 关键词
     * @return 改变的条数
     */
    int insertSearchWord(@Param("searchWord")String searchWord);

    /**
     * 加关注
     * @param concernUserName 关注的人
     * @param concernByUserName 被关注的人
     * @return 改变的条数
     */
    int insertFollow(@Param("concernUserName")String concernUserName, @Param("concernByUserName")String concernByUserName);



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
     * 查询用户是否关注
     * @param userConcernName 关注的用户名
     * @param userByConcernName 被关注的用户名
     * @return 匹配值的个数
     */
    int queryUserConcernWhether(@Param("userConcernName")String userConcernName, @Param("userByConcernName")String userByConcernName);

    /**
     * 根据用户名查询用户关注信息
     * @param userName 用户名
     * @return 关注信息
     */
    List<Map<String, Object>> queryUserConcernInfoByUserName(@Param("userName")String userName);

    /**
     * 根据用户名查询粉丝信息
     * @param userName 用户名
     * @return 粉丝信息
     */
    List<Map<String, Object>> queryUserByConcernInfoByUserName(@Param("userName")String userName);

    /**
     * 查询用户关注数
     * @param userName 用户名
     * @return 关注数
     */
    int queryUserConcernCountByUserName(@Param("userName") String userName);

    /**
     * 查询用户粉丝数
     * @param userName 用户名
     * @return 粉丝数
     */
    int queryUserFanCountByUserName(@Param("userName") String userName);

    /**
     * 查询用户DITS数
     * @param userName 用户名
     * @return dits数
     */
    int queryUserDitsByUserName(@Param("userName") String userName);

    /**
     * 根据用户名查找获赞数
     * @param userName 用户名
     * @return 获赞数
     */
    int queryPraiseCountByUserName(@Param("userName") String userName);

    /**
     * 根据用户名查找文章数
     * @param userName 用户名
     * @return 文章数
     */
    int queryArticleCountByUserName(@Param("userName") String userName);

    /**
     * 获取热搜词
     * @return 热搜词
     */
    List<Map<String, Object>> querySearchKeyWord();

    /**
     * 查找用户主页信息
     * @param userName 用户名
     * @return 用户主页信息
     */
    List<Map<String, Object>> queryUserIndexInfoByUserName(@Param("userName")String userName);

    /**
     * 查询用户7天发送的Dit数
     * @param userName 用户名
     * @return 数据
     */
    List<Map<String, Object>> queryWeekDitInfoByUserName(@Param("userName")String userName);

    /**
     * 搜索用户
     * @param searchWords 关键词
     * @return 搜索数据
     */
    List<UserInfo> searchUser(@Param("searchWord")String searchWords);


    /**
     * 搜索dit
     * @param searchWords 关键词
     * @return 搜索数据
     */
    List<Map<String, Object>> searchDit(@Param("searchWord")String searchWords);

    /**
     * 搜索文章
     * @param searchWords 关键词
     * @return 搜索数据
     */
    List<User> searchArticle(@Param("searchWord")String searchWords);

    /**
     * 检查关键词重复
     * @param searchWord 关键词
     * @return 重复的关键词
     */
    List<Map<String, Object>> searchKeyWordRetrieval(@Param("searchWord")String searchWord);

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

    /**
     * 关键词ID频率加一
     * @param searchId 关键词ID
     * @return 改变数据的条数
     */
    int updateSearchWord(@Param("searchId")String searchId);

    /**
     * 更新用户主页图片
     * @param userName 用户名
     * @param uuid 背景图Uuid
     * @return 改变数据的条数
     */
    int updateUserIndexImg(@Param("indexUserName")String userName, @Param("indexBGIUuid")String uuid);

    /**
     * 删关注
     * @param concernUserName 关注的人
     * @param concernByUserName 被关注的人
     * @return 改变数据的条数
     */
    int deleteFollow(@Param("concernUserName")String concernUserName, @Param("concernByUserName")String concernByUserName);
}

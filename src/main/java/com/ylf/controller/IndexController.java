package com.ylf.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylf.pojo.Dit;
import com.ylf.pojo.User;
import com.ylf.pojo.UserIndex;
import com.ylf.pojo.UserInfo;
import com.ylf.service.IndexService;
import com.ylf.util.ReturnData;
import com.ylf.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Master 2021/2/10
 */
@RestController
@RequestMapping("/userIndex")
public class IndexController {
    @Autowired
    @Qualifier("indexServiceImpl")
    IndexService indexService;

    @RequestMapping("/test")
    public void test(){
        PageHelper.startPage(1,5);
        List<Map<String, Object>> list = indexService.searchDit("1");
        for(Map<String, Object> map:list){
            System.out.println(map);
        }
    }

    @RequestMapping("/queryUserInfo")
    public Object queryUserInfo(@RequestParam Map<String, Object> map){
        UserInfo userInfo = indexService.queryUserInfoByUserName(map.get("userName").toString());
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,userInfo,"用户信息"));
    }

    @RequestMapping("/queryUserCard")
    public Object queryUserCard(@RequestParam Map<String, Object> map){
        UserInfo userInfo = indexService.queryUserInfoByUserName(map.get("userCardName").toString());
        String style = "";
        Map<String, Object> returnMap = new HashMap<>(7);
        putIntoMap(userInfo, returnMap);
        if(Objects.equals(map.get("userCardName"), map.get("userName"))){
            style = "self";
        }
        else if(indexService.queryUserConcernWhether(map.get("userName").toString(),map.get("userCardName").toString()) > 0){
            style = "follow";
        }
        else{
            style = "unfollow";
        }
        returnMap.put("style",style);
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,returnMap,"用户名片信息"));
    }

    @RequestMapping("/queryUserIndex")
    public Object queryUserIndex(@RequestParam Map<String, Object> map){
        UserInfo userInfo = indexService.queryUserInfoByUserName(map.get("userName").toString());
        Map<String, Object> returnMap = new HashMap<>(9);
        putIntoMap(userInfo, returnMap);
        returnMap.put("userByPraise",indexService.queryPraiseCountByUserName(userInfo.getUserName()));
        returnMap.put("userArticle",indexService.queryArticleCountByUserName(userInfo.getUserName()));
        returnMap.put("userVisited",indexService.queryUserIndexInfoByUserName(userInfo.getUserName()).get(0).get("indexVisit").toString());
        returnMap.put("userWeekDit",indexService.queryWeekDitInfoByUserName(userInfo.getUserName()));
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,returnMap,"用户名片信息"));
    }



    @RequestMapping("/queryMainSearchWord")
    public Object queryMainSearchWord(){
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,indexService.querySearchKeyWord(),"关键词"));
    }

    @RequestMapping("/searchUser")
    public Object searchUser(@RequestParam Map<String, Object> map){
        String searchWord = map.get("searchWord").toString();
        List<UserInfo> users = indexService.searchUser(searchWord);

        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,users,"用户搜索信息"));
    }

    @RequestMapping("/searchDits")
    public Object searchDits(@RequestParam Map<String, Object> map){
        String searchWord = map.get("searchWord").toString();
        List<Map<String, Object>> retrievalMap = indexService.searchKeyWordRetrieval(searchWord);
        if(retrievalMap.size() > 0){
            indexService.updateSearchWord(retrievalMap.get(0).get("searchId").toString());
        }
        else{
            if(searchWord.length() > 1){
                indexService.insertSearchWord(searchWord);
            }
        }
        int ditsLength = indexService.searchDit(map.get("searchWord").toString()).size();
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String, Object>> dits = indexService.searchDit(map.get("searchWord").toString());
        if(dits.size() > 0){
            dits.get(0).put("count",ditsLength);
        }
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,dits,"用户搜索信息"));
    }

    @RequestMapping("/follow")
    public Object follow(@RequestParam Map<String, Object> map){
        indexService.insertFollow(map.get("concernUserName").toString(),map.get("concernByUserName").toString());
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"关注成功"));
    }

    @RequestMapping("/unfollow")
    public Object unfollow(@RequestParam Map<String, Object> map){
        indexService.deleteFollow(map.get("concernUserName").toString(),map.get("concernByUserName").toString());
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"取消关注成功"));
    }

    @RequestMapping("queryUserFriend")
    public Object queryUserFriend(@RequestParam Map<String, Object> map){
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("concern",indexService.queryUserConcernInfoByUserName(map.get("userName").toString()));
        returnMap.put("fans",indexService.queryUserByConcernInfoByUserName(map.get("userName").toString()));
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,returnMap,"朋友信息"));
    }

    @RequestMapping("queryBackGroundImg")
    public Object queryBackGroundImg(@RequestParam Map<String, Object> map){
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,
                indexService.queryUserIndexInfoByUserName(map.get("userName").toString()).get(0).get("indexBGIUuid"),
                "背景图Uuid"));
    }

    @RequestMapping("queryUserWeekDit")
    public Object queryUserWeekDit(@RequestParam Map<String, Object> map){
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,
                indexService.queryWeekDitInfoByUserName(map.get("userName").toString()),
                "七天用户Dit信息"));
    }


    @RequestMapping("/updateUserInfo")
    public Object updateUserInfo(@RequestParam Map<String, Object> map){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(map.get("userName").toString());
        userInfo.setUserAge(map.get("userAge").toString());
        userInfo.setUserBirthday(map.get("userBirthday").toString());
        userInfo.setUserNickName(map.get("userNickName").toString());
        userInfo.setUserGender(map.get("userGender").toString());
        userInfo.setUserSign(map.get("userSign").toString());
        int flag = indexService.updateUserInfo(userInfo);
        if(flag == 0){
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"更新用户信息失败"));
        }
        else {
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"更新用户信息成功"));
        }
    }

    @RequestMapping("/updateUserFace")
    public Object updateUserFace(HttpServletRequest request,@RequestParam Map<String, Object> map){
        UserInfo userinfo = indexService.queryUserInfoByUserName(map.get("userName").toString());
        String str=request.getSession().getServletContext()
                .getRealPath("/upload/");
        String oldFacePath=str.substring(0,str.indexOf("out"))+"web\\static\\img\\userFace\\" + userinfo.getUserFaceUuid();
        File oldFace = new File(oldFacePath);
        if(!Objects.equals(userinfo.getUserFaceUuid(), "default.png")){
            if(!oldFace.delete()){
                System.out.println("删除旧图片失败");
            }
        }
        if(indexService.updateUserFace(map.get("userName").toString(),map.get("UUID").toString()) == 0){
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"更新图片UUID失败"));
        }
        else{
            return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"更新图片UUID成功"));
        }
    }

    @RequestMapping("/updateBackGroundImg")
    public Object updateBackGround(HttpServletRequest request,@RequestParam Map<String, Object> map){
        List<Map<String, Object>> index = indexService.queryUserIndexInfoByUserName(map.get("userName").toString());
        String str=request.getSession().getServletContext().getRealPath("/upload/");
        String oldFacePath=str.substring(0,str.indexOf("out"))+"web\\static\\img\\userFace\\" + index.get(0).get("indexBGIUuid");
        File oldFace = new File(oldFacePath);
        if(!Objects.equals(index.get(0).get("indexBGIUuid"), "default.png")){
            if(!oldFace.delete()){
                System.out.println("删除旧图片失败");
            }
        }
        if(indexService.updateUserIndexImg(map.get("userName").toString(),map.get("UUID").toString()) == 0){
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"更新图片UUID失败"));
        }
        else{
            return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"更新图片UUID成功"));
        }
    }

    @RequestMapping("/userFaceUpload")
    public Object axiosFile(HttpServletRequest request,MultipartFile file) {
        String newFileName=UUID.randomUUID().toString().replaceAll("-", "")+ Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        return uploadImg(request,file,"userFace",newFileName);
    }

    @RequestMapping("/userBackGroundUpload")
    public Object uploadBackGround(HttpServletRequest request,MultipartFile file){
        String newFileName=UUID.randomUUID().toString().replaceAll("-", "")+ Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        return uploadImg(request,file,"backGroundImg",newFileName);
    }














    private Object uploadImg(HttpServletRequest request,MultipartFile file,String type,String newFileName){
        try {

            String str=request.getSession().getServletContext()
                    .getRealPath("/upload/");
            String newFilePath1=str.substring(0,str.indexOf("out"))+"web\\static\\img\\"+type+"\\" + newFileName;
            File newFile1=new File(newFilePath1);
            file.transferTo(newFile1);
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,newFileName,"上传头像成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"上传头像失败"));
        }
    }

    private void putIntoMap(UserInfo userInfo, Map<String, Object> returnMap) {
        returnMap.put("userNickName",userInfo.getUserNickName());
        returnMap.put("userName",userInfo.getUserName());
        returnMap.put("userAge",userInfo.getUserAge());
        returnMap.put("userGender",userInfo.getUserGender());
        returnMap.put("userFaceUuid",userInfo.getUserFaceUuid());
        returnMap.put("userFollow",indexService.queryUserConcernCountByUserName(userInfo.getUserName()));
        returnMap.put("userFans",indexService.queryUserFanCountByUserName(userInfo.getUserName()));
        returnMap.put("userDitCount",indexService.queryUserDitsByUserName(userInfo.getUserName()));
        returnMap.put("userSign",userInfo.getUserSign());
    }
}

package com.ylf.controller;

import com.alibaba.fastjson.JSONObject;
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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

    }

    @RequestMapping("/queryUserInfo")
    public Object queryUserInfo(@RequestParam Map<String, Object> map){
        UserInfo userInfo = indexService.queryUserInfoByUserName(map.get("userName").toString());
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,userInfo,"用户信息"));
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
        if(!oldFace.delete()){
            System.out.println("删除旧图片失败");
        }
        if(indexService.updateUserFace(map.get("userName").toString(),map.get("UUID").toString()) == 0){
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"更新图片UUID失败"));
        }
        else{
            return null;
        }
    }

    @RequestMapping("/userFaceUpload")
    public Object axiosFile(HttpServletRequest request,MultipartFile file,@RequestParam Map<String, Object> map) {
        try {
            String newFileName=UUID.randomUUID().toString().replaceAll("-", "")+ Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String str=request.getSession().getServletContext()
                    .getRealPath("/upload/");
            String newFilePath1=str.substring(0,str.indexOf("out"))+"web\\static\\img\\userFace\\" + newFileName;
            File newFile1=new File(newFilePath1);
            file.transferTo(newFile1);
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,newFileName,"上传头像成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"上传头像失败"));
        }
    }
}

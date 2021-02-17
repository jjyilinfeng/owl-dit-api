package com.ylf.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylf.pojo.UserIp;
import com.ylf.util.ReturnData;
import com.ylf.util.StatusCode;
import com.ylf.pojo.User;
import com.ylf.service.BookService;
import com.ylf.util.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Master 2021/2/7
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;
    @RequestMapping("/login")
    public Object loginCheck(HttpServletRequest request, @RequestParam Map<String, Object> map){
        List<User> checkUser;
        checkUser = bookService.checkLogin(map.get("userName").toString(),map.get("password").toString());
        if(checkUser.size() == 0){
            return  JSONObject.toJSON(new ReturnData(StatusCode.LOGIN_FAILED,"登录失败"));
        }
        else if(Objects.equals(checkUser.get(0).getUserType(), "administrator")){
            return  JSONObject.toJSON(new ReturnData(StatusCode.LOGIN_SUCCESS,"管理员登录成功"));
        }
        else if(Objects.equals(checkUser.get(0).getUserType(), "user")){
            String localIp = Functions.getIp(request);
            boolean flag = false;
            for (User user : checkUser) {
                if (Objects.equals(user.getUserIp(), localIp)) {
                    flag = true;
                    break;
                }
            }
            if(flag){
                checkUser.get(0).setUserLoginTime(Functions.getNowTime("yyyy-MM-dd HH:mm:ss"));
                checkUser.get(0).setUserLoginIp(localIp);
                System.out.println(checkUser.get(0));
                bookService.updateUserInf(checkUser.get(0));
                return  JSONObject.toJSON(new ReturnData(StatusCode.LOGIN_SUCCESS,"用户登录成功"));
            }
            else{
                return  JSONObject.toJSON(new ReturnData(StatusCode.LOGIN_IP_ABNORMAL ,"用户登录IP异常"));
            }
        }
        else{
            return JSONObject.toJSON(new ReturnData(StatusCode.LOGIN_ERROR,"登录异常"));
        }
    }

    @RequestMapping("/insertUserIp")
    public void insertUserIp(HttpServletRequest request,@RequestParam Map<String, Object> map){
        bookService.insertUserIp(map.get("userName").toString(),Functions.getIp(request));
    }

    @RequestMapping("/checkRe")
    public Object checkRe(@RequestParam Map<String, Object> map){
        String style = map.get("style").toString();
        Integer result;
        switch (style){
            case "userName":
                result = bookService.checkRename(map.get("userName").toString());
                if(result != null){
                    return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"用户已经被使用"));
                }
                else{
                    return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"用户可以使用"));
                }
            case  "userEmail":
                result = bookService.checkReEmail(map.get("userEmail").toString());
                if(result != null){
                    return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"邮箱已经被使用"));
                }
                else{
                    return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"邮箱可以使用"));
                }
            default:
                return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"重复检测异常"+style));
        }
    }

    @RequestMapping("queryUserSafeInfo")
    public Object queryUserSafeInfo(@RequestParam Map<String, Object> map){
        try {
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,
                    bookService.queryUserInfoByName(map.get("userName").toString()),"查询成功"));
        }catch (Exception e){
            e.printStackTrace();
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"查询失败"));
        }
    }

    @RequestMapping("updateUserSafeInfo")
    public Object updateUserSafeInfo(@RequestParam Map<String, Object> map){
        if(map.get("userPassword") != null){
            User user = bookService.queryUserInfoByName(map.get("userName").toString());
            user.setUserPassword(map.get("userPassword").toString());
            return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,bookService.updateUserInf(user),"用户安全信息更新成功"));
        }
        else if(map.get("userEmail") != null){
            User user = bookService.queryUserInfoByName(map.get("userName").toString());
            user.setUserEmail(map.get("userEmail").toString());
            return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,bookService.updateUserInf(user),"用户安全信息更新成功"));
        }
        else{
            return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"用户安全信息更新失败"));
        }
    }

    @RequestMapping("/queryUserIp")
    public Object queryUserIp(@RequestParam Map<String, Object> map){
        List<UserIp> userIps = bookService.queryUserIpByName(map.get("userName").toString());
        return JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,userIps,"用户IP查询成功"));
    }

    @RequestMapping("/sendCheckCode")
    public void sendCheckCode(@RequestParam Map<String, Object> map){
        if(map.get("userEmail") == null){
            User user = bookService.queryUserInfoByName(map.get("userName").toString());
            bookService.sendEmail(map.get("checkCode").toString(),user.getUserEmail());
        }
        else{
            bookService.sendEmail(map.get("checkCode").toString(),map.get("userEmail").toString());
        }

    }

    @RequestMapping("/register")
    public Object register(HttpServletRequest request,@RequestParam Map<String, Object> map){
        User user = new User();
        String localIp = Functions.getIp(request);
        user.setUserName(map.get("userName").toString());
        user.setUserPassword(map.get("password").toString());
        user.setUserEmail(map.get("userEmail").toString());
        user.setUserLoginTime(Functions.getNowTime("yyyy-MM-dd HH:mm:ss"));
        user.setUserIp(localIp);
        int flag = bookService.insertUser(user);
        if(flag > 0){
            bookService.insertUserIp(map.get("userName").toString(),localIp);
            bookService.initUserInfo(map.get("userName").toString());
            bookService.insertUserIndex(map.get("userName").toString());
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,"注册成功"));
        }
        else{
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"注册失败"));
        }
    }



    @RequestMapping("/test")
    public void test(){
        System.out.println("123456");
    }

}

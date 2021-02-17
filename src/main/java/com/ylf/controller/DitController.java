package com.ylf.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylf.pojo.Dit;
import com.ylf.service.DitService;
import com.ylf.util.Functions;
import com.ylf.util.ReturnData;
import com.ylf.util.StatusCode;
import jdk.nashorn.internal.objects.annotations.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author Master 2021/2/12
 */
@RestController
@RequestMapping("/dit")
public class DitController {
    @Autowired
    @Qualifier("ditServiceImpl")
    private DitService ditService;

    @RequestMapping("test")
    public void test(){
        ArrayList<String> arr = new ArrayList<>();
        arr.add("abc");
        arr.add("dfdf");
        Map<String, Object> map = new HashMap<>(1);
        map.put("ditImgUuid",arr);
        map.put("ditUuid","werwerwr");
        ditService.insertDitImg(map);
    }

    @RequestMapping("/createDit")
    public Object createDit(@RequestParam Map<String, Object> map){

        String ditUuid = Functions.getUuid();
        List<String> i = Arrays.asList(map.get("ditImg").toString().split("&"));

        Map<String, Object> imgMap = new HashMap<>(1);
        imgMap.put("ditImgUuid",i);
        imgMap.put("ditUuid",ditUuid);

        ditService.insertDitImg(imgMap);

        Dit dit = new Dit();
        dit.setDitUserName(map.get("userName").toString());
        dit.setDitMessage(map.get("ditMessage").toString());
        dit.setDitDateTime(Functions.getNowTime("yyyy-MM-dd HH:mm:ss"));
        dit.setDitUuid(ditUuid);
        int flag = ditService.insertDit(dit);
        return  JSONObject.toJSON(new ReturnData(
                flag>0?StatusCode.REQUEST_SUCCESS:StatusCode.REQUEST_FALSE,
                flag>0?"发送DIT成功":"发送dit失败"));
    }

    @RequestMapping("getAllDit")
    public Object getAllDit(@RequestParam Map<String, Object> map){
        int pageSize = 5;
        int page = Integer.parseInt(map.get("page").toString());
        int limit = page*pageSize;
        int pageMax = ditService.queryAllDitCount(map.get("userName").toString());
        List<Map<String, Object>> dits = ditService.queryAllDit(map.get("userName").toString(),limit);
        if(dits.size() > 0){
            dits.get(0).put("pageMax",pageMax / pageSize+1);
        }
        return getDitData(dits);
    }

    @RequestMapping("getSelfDit")
    public Object getSelfDit(@RequestParam Map<String, Object> map){
        int pageSize = 5;
        int page = Integer.parseInt(map.get("page").toString());
        int limit = page*pageSize;
        int pageMax = ditService.queryAllDitCount(map.get("userName").toString());
        List<Map<String, Object>> dits = ditService.querySelfDit(map.get("userName").toString(),limit);
        if(dits.size() > 0){
            dits.get(0).put("pageMax",pageMax / pageSize+1);
        }
        return getDitData(dits);
    }



    @RequestMapping("insertDitPraise")
    public Object insertDitPraise(@RequestParam Map<String, Object> map){
        ditService.insertDitPraise(map.get("ditPraiseUserName").toString(),map.get("ditUuid").toString());
        return  JSONObject.toJSON(new ReturnData(
               StatusCode.REQUEST_SUCCESS,
                ditService.queryDitPraiseByUuid(map.get("ditUuid").toString()),
                "返回信息"));
    }

    @RequestMapping("insertDitComment")
    public Object insertDitComment(@RequestParam Map<String, Object> map){
        ditService.insertDitComment(map.get("ditCommentUserName").toString(),
                map.get("ditCommentMessage").toString(),map.get("ditUuid").toString());
        return  JSONObject.toJSON(new ReturnData(
                StatusCode.REQUEST_SUCCESS,
                ditService.queryDitCommentByUuid(map.get("ditUuid").toString()),
                "返回信息"));
    }

    @RequestMapping("deleteDitPraise")
    public Object deleteDitPraise(@RequestParam Map<String, Object> map){
        ditService.deleteDitPraise(map.get("ditPraiseUserName").toString(),map.get("ditUuid").toString());
        return  JSONObject.toJSON(new ReturnData(
                StatusCode.REQUEST_SUCCESS,
                ditService.queryDitPraiseByUuid(map.get("ditUuid").toString()),
                "取消赞成功"));
    }

    @RequestMapping("deleteDitComment")
    public Object deleteDitComment(@RequestParam Map<String, Object> map){
        int num = ditService.deleteDitCommentById(Integer.parseInt(map.get("id").toString()));
        return  JSONObject.toJSON(new ReturnData(
                num>0?StatusCode.REQUEST_SUCCESS:StatusCode.REQUEST_FALSE,
                num,
                num>0?"删除评论成功":"删除评论失败"));
    }

    @RequestMapping("deleteDit")
     public Object deleteDit(HttpServletRequest request,@RequestParam Map<String, Object> map){
        List<Map<String, String>> deleteImg = ditService.queryDitImgByDitUuid(map.get("ditUuid").toString());
        for(Map<String, String> img : deleteImg){
            String str=request.getSession().getServletContext().getRealPath("/upload/");
            String oldFacePath=str.substring(0,str.indexOf("out"))+"web\\static\\img\\ditImg\\" + img.get("ditImgUuid");
            File oldFace = new File(oldFacePath);
            if(!oldFace.delete()){
                System.out.println("删除旧图片失败");
            }
        }

        int flag = ditService.deleteDitByUuid(map.get("ditUuid").toString());
        return  JSONObject.toJSON(new ReturnData(
                flag>0?StatusCode.REQUEST_SUCCESS:StatusCode.REQUEST_FALSE,
                flag>0?"删除Dit成功":"删除Dit为空"));
    }

    @RequestMapping("/ditImgUpload")
    public Object axiosFile(HttpServletRequest request, MultipartFile file, @RequestParam Map<String, Object> map) {
        try {
            String newFileName= UUID.randomUUID().toString().replaceAll("-", "")+ Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String str=request.getSession().getServletContext()
                    .getRealPath("/upload/");
            String newFilePath1=str.substring(0,str.indexOf("out"))+"web\\static\\img\\ditImg\\" + newFileName;
            File newFile1=new File(newFilePath1);
            file.transferTo(newFile1);

            Map<String, String> img = new HashMap<>(1);
            img.put("src","/api/static/img/ditImg/"+newFileName);
            img.put("title",newFileName);
            return  JSONObject.toJSON(new ReturnData(0,img,"上传成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_FALSE,"上传失败"));
        }
    }









    private <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    private Object getDitData(List<Map<String, Object>> dits) {
        for (Map<String, Object> dit : dits) {
            dit.put("ditPraise", ditService.queryDitPraiseByUuid(dit.get("ditUuid").toString()));
            dit.put("ditComment",ditService.queryDitCommentByUuid(dit.get("ditUuid").toString()));
            dit.put("ditImg",ditService.queryDitImgByDitUuid(dit.get("ditUuid").toString()));
        }
        return  JSONObject.toJSON(new ReturnData(
                dits.size()>0? StatusCode.REQUEST_SUCCESS:StatusCode.REQUEST_FALSE,
                dits,
                dits.size()>0?"查询DIT成功":"查询dit为空"));
    }
}

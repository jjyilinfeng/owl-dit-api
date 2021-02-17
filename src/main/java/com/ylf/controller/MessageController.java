package com.ylf.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylf.service.DitService;
import com.ylf.service.IndexService;
import com.ylf.util.ReturnData;
import com.ylf.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Master 2021/2/15
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    @Qualifier("ditServiceImpl")
    private DitService ditService;

    @RequestMapping("unReadPraise")
    public Object unreadPraise(@RequestParam Map<String, Object> map){
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,
                ditService.queryDitPraiseUnNoticeByUserName(map.get("userName").toString()),
                "未读赞"));
    }

    @RequestMapping("unReadComment")
    public Object unreadComment(@RequestParam Map<String, Object> map){
        return  JSONObject.toJSON(new ReturnData(StatusCode.REQUEST_SUCCESS,
                ditService.queryDitCommentUnNoticeByUserName(map.get("userName").toString()),
                "未读评论"));
    }
}

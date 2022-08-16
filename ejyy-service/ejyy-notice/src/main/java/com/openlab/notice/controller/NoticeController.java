package com.openlab.notice.controller;

import com.alibaba.fastjson.JSON;
import com.openlab.common.utils.Result;
import com.openlab.common.utils.ResultCodeEnum;
import com.openlab.common.utils.TokenManager;
import com.openlab.notice.Entity.NoticeToUser;
import com.openlab.notice.Entity.NoticeTpl;
import com.openlab.notice.service.NoticeToUserService;
import com.openlab.notice.service.NoticeTplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeToUserService noticeToUserService;

    @Autowired
    private NoticeTplService noticeTplService;

    @GetMapping("/tpl")
    public Result getTpl(){
        List<Map<String,String>> res = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        map1.put("title","停水通知");
        map1.put("tpl","");
        map1.put("content","");
        map2.put("title","停电通知");
        map2.put("tpl","");
        map2.put("content","");
        res.add(map1);
        res.add(map2);
        Map list = new HashMap<>();
        list.put("list", res);
        return Result.ok(ResultCodeEnum.SUCCESS.code, list);
    }

    @PostMapping("/create")
    public Result create(@RequestBody Map<String,Object> noticeContent, HttpServletRequest httpServletRequest){

        Long userId = TokenManager.getNameByJwtToken(httpServletRequest).getId();

        NoticeTpl noticeTpl = NoticeTpl.builder()
                .tpl((String) noticeContent.get("tpl"))
                .content(JSON.toJSONString(noticeContent.get("tpl_content")))
                .build();
        noticeTplService.save(noticeTpl);

        long created_at = System.currentTimeMillis();
        Integer published = (Integer)noticeContent.get("published");
        Long published_at = published == 1 ? created_at : null;
        Long published_by = published == 1 ? userId : null;

        NoticeToUser noticeToUser = NoticeToUser.builder()
                .title((String) noticeContent.get("title"))
                .overview((String) noticeContent.get("overview"))
                .published(published)
                .content(JSON.toJSONString(noticeContent.get("content")))
                .community_id(Long.valueOf(String.valueOf(noticeContent.get("community_id"))))
                .created_by(userId)
                .published_at(published_at)
                .published_by(published_by)
                .notice_tpl_id(noticeTpl.getId())
                .created_at(created_at)
                .build();
        noticeToUserService.save(noticeToUser);
        return Result.ok();
    }
}

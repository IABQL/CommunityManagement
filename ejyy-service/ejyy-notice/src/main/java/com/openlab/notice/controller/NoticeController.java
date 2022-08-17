package com.openlab.notice.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.openlab.common.utils.Result;
import com.openlab.common.utils.ResultCodeEnum;
import com.openlab.notice.Entity.NoticeToUser;
import com.openlab.notice.Entity.NoticeTpl;
import com.openlab.notice.dto.NoticeListDto;
import com.openlab.notice.rabbit.RabbitProvider;
import com.openlab.notice.service.NoticeListService;
import com.openlab.notice.service.NoticeToUserService;
import com.openlab.notice.service.NoticeTplService;
import com.openlab.notice.utils.HostHolder;
import com.openlab.notice.vo.NoticeListVo;
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

    @Autowired
    private RabbitProvider rabbitProvider;

    @Autowired
    private NoticeListService noticeListService;

    @Autowired
    private HostHolder hostHolder;

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

        Long userId = hostHolder.getUserId();

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

        Map<String,Object> res = new HashMap<>();
        res.put("id",noticeToUser.getId());
        // TODO................................
        //rabbitProvider.sendMessage();// 消息队列
        return Result.ok(ResultCodeEnum.SUCCESS.code, res);
    }

    @PostMapping("/list")
    public Result getNoticeList(@RequestBody NoticeListDto noticeListDto){
        int offset = (noticeListDto.getPage_num()-1) * noticeListDto.getPage_size();
        noticeListDto.setOffset(offset);
        List<NoticeListVo> noticeList = noticeListService.getNoticeList(noticeListDto);
        int noticeTotal = noticeListService.getNoticeTotal(noticeListDto);

        Map<String,Object> res = new HashMap<>();
        res.put("list",noticeList);
        res.put("page_amount",Math.ceil(noticeTotal / noticeListDto.getPage_size()));
        res.put("total",noticeTotal);
        res.put("page_num",noticeListDto.getPage_num());
        res.put("page_size",noticeListDto.getPage_size());

        return Result.ok(ResultCodeEnum.SUCCESS.code, res);
    }

    @PostMapping("/detail")
    public Result getNoticeDetail(@RequestBody Map<String,Long> map) {
        Map<String, Object> noticeDetail = noticeListService.getNoticeDetail(map.get("id"),map.get("community_id"));
        String tpl_title = "非法模板";
        if (noticeDetail.get("tpl") != null){

        }
        noticeDetail.put("tpl_title",tpl_title);
        noticeDetail.put("published_real_name",noticeDetail.get("real_name"));
        return Result.ok(ResultCodeEnum.SUCCESS.code, noticeDetail);
    }

    @PostMapping("/published")
    public Result published(@RequestBody Map<String,Long> map){
        Long id = map.get("id");
        Long community_id = map.get("community_id");

        Long published_at = System.currentTimeMillis();

        QueryWrapper<NoticeToUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("community_id", community_id);
        NoticeToUser notice = noticeToUserService.getOne(queryWrapper);
        if (notice == null){
            return Result.error(ResultCodeEnum.QUERY_ILLEFAL.code, "非法小区通知");
        }
        if (notice.getPublished() == 1){
            return Result.error(ResultCodeEnum.STATUS_ERROR.code, "通知已发布");
        }

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("published",true);
        updateWrapper.set("published_by",hostHolder.getUserId());
        updateWrapper.set("published_at",published_at);
        updateWrapper.eq("id",id);
        updateWrapper.eq("community_id",community_id);
        boolean update = noticeToUserService.update(updateWrapper);
        if (!update){
            return Result.error(ResultCodeEnum.DATA_MODEL_UPDATE_FAIL.code,"发布通知失败");
        }

        Map<String,Object> data = new HashMap<>();
        data.put("published_at", published_at);
        return Result.ok(ResultCodeEnum.SUCCESS.code, "修改小区通知成功",data);
    }
}

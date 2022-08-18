package com.openlab.notice.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.openlab.common.utils.Result;
import com.openlab.common.utils.ResultCode;
import com.openlab.common.utils.ResultCodeEnum;
import com.openlab.notice.Entity.Notice;
import com.openlab.notice.Entity.NoticeToUser;
import com.openlab.notice.Entity.NoticeTpl;
import com.openlab.notice.Entity.ScreenDetail;
import com.openlab.notice.dto.NoticeListDto;
import com.openlab.notice.rabbit.RabbitProvider;
import com.openlab.notice.service.NoticeListService;
import com.openlab.notice.service.NoticeToUserService;
import com.openlab.notice.service.NoticeTplService;
import com.openlab.notice.utils.HostHolder;
import com.openlab.notice.vo.NoticeListVo;
import com.openlab.notice.vo.ScreenRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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


    @PostMapping("/create")
    public Result create(@RequestBody Map<String,Object> noticeContent, HttpServletRequest httpServletRequest){

        Long userId = hostHolder.getUserId();// 获取当前用户

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

        Notice notice = new Notice()
                .setTitle((String) noticeContent.get("title"))
                .setOverview((String) noticeContent.get("overview"));
        ScreenDetail screenDetail = ScreenRandom.random(notice);
        rabbitProvider.sendMessage(JSON.toJSONString(screenDetail));// 消息队列
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
        String content = noticeDetail.get("content").toString();
        String s = content.replaceAll("\"", "'");

        noticeDetail.put("content",s);
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

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> updateParam) {

        String content = JSON.toJSONString(updateParam.get("content"));

        QueryWrapper<NoticeToUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", updateParam.get("id"));
        queryWrapper.eq("community_id", updateParam.get("community_id"));

        NoticeToUser detail = noticeToUserService.getOne(queryWrapper);

        if (ObjectUtils.isEmpty(detail)) {
            return Result.error(ResultCode.QUERY_ILLEFAL, "非法的小区通知");
        }
        if (detail.getPublished() == 1) {
            return Result.error(ResultCode.STATUS_ERROR, "已发布的通知不能更新");
        }
        Long noticeTplId = detail.getNotice_tpl_id();
        if (!ObjectUtils.isEmpty(updateParam.get("oa_tpl_msg"))) {
            if (!ObjectUtils.isEmpty(noticeTplId)) {
                UpdateWrapper<NoticeTpl> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", detail.getNotice_tpl_id());
                updateWrapper.set("tpl", updateParam.get("tpl"));
                updateWrapper.set("content", content);

                noticeTplService.update(updateWrapper);
            } else {
                noticeTplService.save(NoticeTpl.builder()
                        .tpl((String) updateParam.get("tpl"))
                        .content(String.valueOf(content))
                        .build());
            }
        } else {
            if (ObjectUtils.isEmpty(noticeTplId)) {
                noticeTplService.removeById(detail.getNotice_tpl_id());
                noticeTplId = null;
            }
        }
        UpdateWrapper<NoticeToUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", detail.getId());
        updateWrapper.eq("community_id", detail.getCommunity_id());
        updateWrapper.set("title", updateParam.get("title"));
        updateWrapper.set("overview", updateParam.get("overview"));
        updateWrapper.set("content", content);
        updateWrapper.set("published", detail.getPublished());
        updateWrapper.set("published_at", ObjectUtils.isEmpty(detail.getPublished()) ? new Date().getTime() : null);

        boolean update = noticeToUserService.update(updateWrapper);

        if (!update) {
            return Result.error(ResultCode.DATA_MODEL_UPDATE_FAIL, "修改小区通知失败");
        }

        return Result.ok(200, "修改小区通知成功");
    }
}

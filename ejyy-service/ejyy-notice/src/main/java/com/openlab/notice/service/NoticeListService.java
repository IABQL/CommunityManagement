package com.openlab.notice.service;

import com.openlab.notice.dto.NoticeListDto;
import com.openlab.notice.vo.NoticeListVo;

import java.util.List;
import java.util.Map;

public interface NoticeListService {
    List<NoticeListVo> getNoticeList(NoticeListDto noticeListDto);

    int getNoticeTotal(NoticeListDto noticeListDto);

    Map<String,Object> getNoticeDetail(Long id, Long communityId);
}

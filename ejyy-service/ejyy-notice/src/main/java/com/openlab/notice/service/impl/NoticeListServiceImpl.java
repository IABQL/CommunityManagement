package com.openlab.notice.service.impl;

import com.openlab.notice.dto.NoticeListDto;
import com.openlab.notice.mapper.NoticeMapper;
import com.openlab.notice.service.NoticeListService;
import com.openlab.notice.vo.NoticeListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticeListServiceImpl implements NoticeListService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<NoticeListVo> getNoticeList(NoticeListDto noticeListDto) {
        return noticeMapper.selectNotice(noticeListDto);
    }

    @Override
    public int getNoticeTotal(NoticeListDto noticeListDto) {
        return noticeMapper.selectNoticeTotal(noticeListDto);
    }

    @Override
    public Map<String,Object> getNoticeDetail(Long id, Long communityId) {
        return noticeMapper.selectNoticeDetail(id, communityId);
    }

}

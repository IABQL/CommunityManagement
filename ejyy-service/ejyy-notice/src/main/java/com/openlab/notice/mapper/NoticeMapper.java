package com.openlab.notice.mapper;

import com.openlab.notice.dto.NoticeListDto;
import com.openlab.notice.vo.NoticeListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {
    List<NoticeListVo> selectNotice(NoticeListDto noticeListDto);
    int selectNoticeTotal(NoticeListDto noticeListDto);
    Map<String,Object> selectNoticeDetail(Long id, Long communityId);
}

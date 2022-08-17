package com.openlab.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListDto {
    private int page_num;
    private int page_size;
    private int offset;
    private Long community_id;
    private Integer published;
}

package com.openlab.notice.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NoticeListVo {
    private Long id;
    private String title;
    private int published;
    private Long published_at;
    private Long notice_tpl_id;
    private Long created_by;
    private Long created_at;
    private String real_name;
}

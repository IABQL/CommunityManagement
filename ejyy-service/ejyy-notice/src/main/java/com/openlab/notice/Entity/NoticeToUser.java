package com.openlab.notice.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeToUser {
    private String title;
    private String overview;
    private int published;
    private String content;
    private Long community_id;
    private Long created_by;// 创建者
    private Long published_at;
    private Long published_by;// 发表者
    private Long notice_tpl_id;
    private Long created_at;
}

package com.openlab.service.user.dto;

import lombok.Data;

/**
 * 小区临时表
 */
@Data
public class CommunityDto {
    private Long communityId;  // 小区ID
    private String name;  // 小区名称
    // 小区设置
    private boolean accessNfc;
    private boolean accessQrcode;
    private boolean accessRemote;
    private boolean fitmentPledge;
}

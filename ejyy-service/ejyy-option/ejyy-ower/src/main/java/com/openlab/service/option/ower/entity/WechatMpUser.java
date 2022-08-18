package com.openlab.service.option.ower.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatMpUser implements Serializable {
    private Long id;
    private String openId;
    private String unionId;
    private String nickName;
    private String realName;
    private String idcard;
    private String phone;
    private String avatarUrl;
    private Integer gender;
    private String signature="不一定每天都很好，但每天都会有些小美好在等你";
    private boolean intact;
    private Long createdAt;
}

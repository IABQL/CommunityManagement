package com.openlab.service.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/16
 * \* Time: 21:40
 * \* Description:
 * \小区基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityInfo implements Serializable {
    private Long id;
    private String name; //名字
    private String banner;//照片
    private String phone;//电话
    private String province; //省
    private String city; //城市
    private String district; //区
    private Long createdBy;
    private Long createdAt;
}

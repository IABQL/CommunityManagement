package com.openlab.service.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:11
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet implements Serializable {
    private Long id;
    private Long wechatMpUserId;
    private Long communityId;
    private Integer petType;
    private String name;
    private Integer sex;
    private String photo;
    private String coatColor;
    private String breed;
    private String petLicense;
    private Long petLicenseAwardAt;
    private Integer remove ;
    private Integer removeReason ;
    private Long removedAt;
    private Long createdAt;
}

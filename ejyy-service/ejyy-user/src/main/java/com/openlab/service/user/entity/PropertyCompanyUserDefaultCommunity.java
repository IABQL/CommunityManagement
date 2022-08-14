package com.openlab.service.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 个人默认的小区
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyCompanyUserDefaultCommunity implements Serializable {
    private Long id;
    private Long propertyCompanyUserId; // 公司编号
    private Long communityId; // 小区编号
}

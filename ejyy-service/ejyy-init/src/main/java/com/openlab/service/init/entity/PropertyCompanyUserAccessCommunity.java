package com.openlab.service.init.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 个人和小区的中间表实现类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyCompanyUserAccessCommunity implements Serializable {
    private Long id;
    private Long propertyCompanyUserId; // 个人信息的编号
    private Long communityId; // 小区的编号
}

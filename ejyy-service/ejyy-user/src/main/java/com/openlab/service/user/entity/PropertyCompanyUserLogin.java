package com.openlab.service.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人个登录的日志表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyCompanyUserLogin {
    private Integer id;
    private Long propertyCompanyUserId; // 人个信息的编号
    private String ip; // 登录IP
    private String userAgent; // 登录客户端浏览器信息
    private Long loginAt; // 登录时间
}

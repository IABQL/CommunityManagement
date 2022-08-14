package com.openlab.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.user.entity.PropertyCompanyUserDefaultCommunity;

/**
 * 默认小区业务层
 */
public interface PropertyCompanyUserDefaultCommunityService extends IService<PropertyCompanyUserDefaultCommunity> {
    Long getCommunityId(Long userId);
}

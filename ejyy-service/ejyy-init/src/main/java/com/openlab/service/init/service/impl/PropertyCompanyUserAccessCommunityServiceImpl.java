package com.openlab.service.init.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.init.entity.PropertyCompanyUserAccessCommunity;
import com.openlab.service.init.mapper.PropertyCompanyUserAccessCommunityMapper;
import com.openlab.service.init.service.PropertyCompanyUserAccessCommunityService;
import org.springframework.stereotype.Service;

/**
 * 个人和小区中间表
 */
@Service
public class PropertyCompanyUserAccessCommunityServiceImpl extends ServiceImpl<PropertyCompanyUserAccessCommunityMapper, PropertyCompanyUserAccessCommunity> implements PropertyCompanyUserAccessCommunityService {
}

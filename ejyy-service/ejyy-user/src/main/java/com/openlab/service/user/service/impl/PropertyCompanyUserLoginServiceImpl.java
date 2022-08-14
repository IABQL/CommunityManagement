package com.openlab.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.user.entity.PropertyCompanyUserLogin;
import com.openlab.service.user.mapper.PropertyCompanyUserLoginMapper;
import com.openlab.service.user.service.PropertyCompanyUserLoginService;
import org.springframework.stereotype.Service;

/**
 * 登录日志
 */
@Service
public class PropertyCompanyUserLoginServiceImpl extends ServiceImpl<PropertyCompanyUserLoginMapper, PropertyCompanyUserLogin> implements PropertyCompanyUserLoginService {
}

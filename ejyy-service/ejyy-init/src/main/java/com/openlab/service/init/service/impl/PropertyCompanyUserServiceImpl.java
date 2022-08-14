package com.openlab.service.init.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.init.entity.PropertyCompanyUser;
import com.openlab.service.init.mapper.PropertyCompanyUserMapper;
import com.openlab.service.init.service.PropertyCompanyUserService;
import org.springframework.stereotype.Service;

/**
 * 个人信息业务层实现类
 */
@Service
public class PropertyCompanyUserServiceImpl extends ServiceImpl<PropertyCompanyUserMapper, PropertyCompanyUser> implements PropertyCompanyUserService {
}

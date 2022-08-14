package com.openlab.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.user.entity.PropertyCompanyAuth;
import com.openlab.service.user.mapper.PropertyCompanyAuthMapper;
import com.openlab.service.user.service.PropertyCompanyAuthService;
import org.springframework.stereotype.Service;

/**
 * 个人令牌和公司的中间表实体类
 */
@Service
public class PropertyCompanyAuthServiceImpl extends ServiceImpl<PropertyCompanyAuthMapper, PropertyCompanyAuth> implements PropertyCompanyAuthService {
}

package com.openlab.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.user.dto.DepartJob;
import com.openlab.service.user.dto.LoginInfo;
import com.openlab.service.user.entity.PropertyCompanyUser;
import com.openlab.service.user.mapper.PropertyCompanyUserMapper;
import com.openlab.service.user.service.PropertyCompanyUserService;
import org.springframework.stereotype.Service;

/**
 * 个人信息登录
 */
@Service
public class PropertyCompanyUserServiceImpl extends ServiceImpl<PropertyCompanyUserMapper, PropertyCompanyUser> implements PropertyCompanyUserService {
    // 实现登录功能
    @Override
    public LoginInfo login(String account) {
        return baseMapper.login(account);
    }

    @Override
    public DepartJob info(Integer userId) {
        return baseMapper.getInfoByUserId(userId);
    }
}

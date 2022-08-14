package com.openlab.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.user.dto.DepartJob;
import com.openlab.service.user.dto.LoginInfo;
import com.openlab.service.user.entity.PropertyCompanyUser;

/**
 * 个人信息登录
 */
public interface PropertyCompanyUserService extends IService<PropertyCompanyUser> {
    LoginInfo login(String account);
    // 查询用户部门和职位
    DepartJob info(Integer userId);
}

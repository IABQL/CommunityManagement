package com.openlab.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import com.openlab.common.dto.PropertyCompanyUser;
import com.openlab.service.user.dto.DepartJob;
import com.openlab.service.user.dto.LoginInfo;

/**
 * 个人信息登录
 */
public interface PropertyCompanyUserService extends IService<PropertyCompanyUser> {
    LoginInfo login(String account);
    // 查询用户部门和职位
    DepartJob info(Integer userId);

    //查询用户的部分信息 (ID和真实姓名)
    CompanyUserPart getCompanyUser(CompanyUserInformation companyUserInformation);
}

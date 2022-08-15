package com.openlab.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.user.dto.LoginInfo;
import com.openlab.service.user.entity.PropertyCompanyAuth;

/**
 * 个人令牌和公司的中间表实体类
 */
public interface PropertyCompanyAuthService extends IService<PropertyCompanyAuth> {
    public boolean logout( LoginInfo userInfo);
}

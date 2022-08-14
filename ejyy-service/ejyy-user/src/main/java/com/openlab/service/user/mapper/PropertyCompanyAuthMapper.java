package com.openlab.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.user.entity.PropertyCompanyAuth;
import org.springframework.stereotype.Repository;

/**
 * 个人令牌和公司的中间表实体类
 */
@Repository
public interface PropertyCompanyAuthMapper extends BaseMapper<PropertyCompanyAuth> {
}

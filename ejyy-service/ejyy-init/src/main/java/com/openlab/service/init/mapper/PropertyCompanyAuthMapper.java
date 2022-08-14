package com.openlab.service.init.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.init.entity.PropertyCompanyAuth;
import org.springframework.stereotype.Repository;

/**
 * 个人令牌和公司的中间表实体类 Mapper 接口
 */
@Repository
public interface PropertyCompanyAuthMapper extends BaseMapper<PropertyCompanyAuth> {
}

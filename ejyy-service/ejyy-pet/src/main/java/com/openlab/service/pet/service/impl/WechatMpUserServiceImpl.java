package com.openlab.service.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.pet.dto.OwerInfo;
import com.openlab.service.pet.entity.WechatMpUser;
import com.openlab.service.pet.mapper.WechatMpUserMapper;
import com.openlab.service.pet.service.WechatMpUserService;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:53
 * \* Description:
 * \
 */
@Service
public class WechatMpUserServiceImpl extends ServiceImpl<WechatMpUserMapper,WechatMpUser> implements WechatMpUserService {
    @Override
    public OwerInfo getOwerInfo(String phone, Integer intact) {
        return baseMapper.getOwerInfo(phone,intact);
    }



}

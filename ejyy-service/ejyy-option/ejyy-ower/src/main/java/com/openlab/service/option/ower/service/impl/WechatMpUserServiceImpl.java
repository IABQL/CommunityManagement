package com.openlab.service.option.ower.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.option.ower.entity.WechatMpUser;
import com.openlab.service.option.ower.mapper.WechatMpUserMapper;
import com.openlab.service.option.ower.service.WechatMpUserService;
import org.springframework.stereotype.Service;

@Service
public class WechatMpUserServiceImpl extends ServiceImpl<WechatMpUserMapper, WechatMpUser> implements WechatMpUserService {
    @Override
    public WechatMpUser getWechatMpUser(String phone, boolean intact) {
        return baseMapper.selectInfo(phone, intact);
    }
}

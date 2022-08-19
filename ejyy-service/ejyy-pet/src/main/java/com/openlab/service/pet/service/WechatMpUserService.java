package com.openlab.service.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.pet.dto.OwerInfo;
import com.openlab.service.pet.entity.WechatMpUser;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:52
 * \* Description:
 * \
 */
public interface WechatMpUserService extends IService<WechatMpUser> {
    OwerInfo getOwerInfo(String phone,Integer intact);


}

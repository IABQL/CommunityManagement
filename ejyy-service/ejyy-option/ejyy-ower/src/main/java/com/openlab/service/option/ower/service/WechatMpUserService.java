package com.openlab.service.option.ower.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.option.ower.entity.WechatMpUser;
import org.apache.ibatis.annotations.Param;

public interface WechatMpUserService extends IService<WechatMpUser> {
    WechatMpUser getWechatMpUser(String phone, boolean intact);

}

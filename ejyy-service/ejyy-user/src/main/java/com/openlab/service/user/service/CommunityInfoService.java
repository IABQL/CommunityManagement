package com.openlab.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.user.dto.CommunityDto;
import com.openlab.service.user.entity.CommunityInfo;

/**
 * 小区信息业务接口
 */
public interface CommunityInfoService extends IService<CommunityInfo> {
    CommunityDto getInfoByUserId(Long userId);
}

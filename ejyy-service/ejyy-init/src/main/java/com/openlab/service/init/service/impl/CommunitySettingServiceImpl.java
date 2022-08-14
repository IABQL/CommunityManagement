package com.openlab.service.init.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.init.entity.CommunitySetting;
import com.openlab.service.init.mapper.CommunitySettingMapper;
import com.openlab.service.init.service.CommunitySettingService;
import org.springframework.stereotype.Service;

/**
 * 小区设置实现类
 */
@Service
public class CommunitySettingServiceImpl extends ServiceImpl<CommunitySettingMapper, CommunitySetting> implements CommunitySettingService {
}

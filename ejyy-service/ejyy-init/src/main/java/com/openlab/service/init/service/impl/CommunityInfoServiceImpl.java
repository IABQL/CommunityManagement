package com.openlab.service.init.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.init.entity.CommunityInfo;
import com.openlab.service.init.mapper.CommunityInfoMapper;
import com.openlab.service.init.service.CommunityInfoService;
import org.springframework.stereotype.Service;

/**
 * 小区业务层实现类
 */
@Service
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements CommunityInfoService {
}

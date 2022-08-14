package com.openlab.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.user.dto.CommunityDto;
import com.openlab.service.user.entity.CommunityInfo;
import com.openlab.service.user.mapper.CommunityInfoMapper;
import com.openlab.service.user.service.CommunityInfoService;
import org.springframework.stereotype.Service;

/**
 * 小区信息业务接口实现类
 */
@Service
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements CommunityInfoService {
    @Override
    public CommunityDto getInfoByUserId(Long userId) {
        return baseMapper.communityInfo(userId);
    }
}

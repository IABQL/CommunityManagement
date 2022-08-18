package com.openlab.service.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.epidemic.entity.UserBuilding;
import com.openlab.service.epidemic.mapper.UserBuildingMapper;
import com.openlab.service.epidemic.service.UserBuildingService;
import org.springframework.stereotype.Service;

@Service
public class UserBuildingServiceImpl extends ServiceImpl<UserBuildingMapper, UserBuilding> implements UserBuildingService {
    @Override
    public UserBuilding findVerify(Integer wechatMapUserId, Integer buildingId, Integer communityId) {
        return baseMapper.selectVerify(wechatMapUserId,buildingId,communityId);
    }
}

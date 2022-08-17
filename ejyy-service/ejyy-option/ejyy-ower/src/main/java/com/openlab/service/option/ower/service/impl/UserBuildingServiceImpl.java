package com.openlab.service.option.ower.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.option.ower.dto.Info;
import com.openlab.service.option.ower.dto.ResultInfo;
import com.openlab.service.option.ower.entity.UserBuilding;
import com.openlab.service.option.ower.mapper.UserBuildingMapper;
import com.openlab.service.option.ower.service.UserBuildingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBuildingServiceImpl extends ServiceImpl<UserBuildingMapper, UserBuilding> implements UserBuildingService {

    @Override
    public List<Info> findBuilding(Long owerInfoId, Integer BINDING_BUILDING, Integer communityId) {
        return baseMapper.selectBuilding(owerInfoId,BINDING_BUILDING,communityId);
    }
}

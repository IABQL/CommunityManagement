package com.openlab.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.user.entity.BuildingInfo;
import com.openlab.service.user.mapper.BuildingInfoMapper;
import com.openlab.service.user.service.BuildingInfoService;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Company: xianoupeng
 * @Author: Jock
 * @Version: 1.0
 */
@Service
public class BuildingInfoServiceImpl extends ServiceImpl<BuildingInfoMapper, BuildingInfo> implements BuildingInfoService {
    @Override
    public Long houseBindingTotal(Long community_id) {
        return baseMapper.houseBindingTotal(community_id);
    }

    @Override
    public Long carportBindingTotal(Long community_id) {
        return baseMapper.houseBindingTotal(community_id);
    }

    @Override
    public Long warehouseBindingTotal(Long community_id) {
        return baseMapper.warehouseBindingTotal(community_id);
    }

    @Override
    public Long merchantBindingTotal(Long community_id) {
        return baseMapper.houseBindingTotal(community_id);
    }

    @Override
    public Long garageBindingTotal(Long community_id) {
        return baseMapper.garageBindingTotal(community_id);
    }

    @Override
    public Long owerTotal(Long community_id) {
        return baseMapper.owerTotal(community_id);
    }

    @Override
    public Long carTotal(Long community_id) {
        return baseMapper.carTotal(community_id);
    }

    @Override
    public Long petTotal(Long community_id) {
        return baseMapper.petTotal(community_id);
    }
}

package com.openlab.service.option.ower.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.option.ower.dto.Info;
import com.openlab.service.option.ower.dto.ResultInfo;
import com.openlab.service.option.ower.entity.UserBuilding;

import java.util.List;

public interface UserBuildingService extends IService<UserBuilding> {
    List<Info> findBuilding(Long owerInfoId, Integer BINDING_BUILDING, Integer communityId);
}

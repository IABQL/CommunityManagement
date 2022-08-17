package com.openlab.service.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.epidemic.entity.UserBuilding;
import org.apache.ibatis.annotations.Param;

public interface UserBuildingService extends IService<UserBuilding> {
    UserBuilding findVerify(Integer wechatMapUserId,  Integer buildingId,  Integer communityId);
}

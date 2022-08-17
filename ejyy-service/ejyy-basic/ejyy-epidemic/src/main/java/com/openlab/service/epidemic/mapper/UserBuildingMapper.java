package com.openlab.service.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.epidemic.entity.UserBuilding;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBuildingMapper extends BaseMapper<UserBuilding> {
    @Select("select ejyy_user_building.*  " +
            "from ejyy_user_building " +
            "left join ejyy_building_info on ejyy_building_info.id=ejyy_user_building.building_id " +
            "where ejyy_user_building.wechat_mp_user_id=#{wechat_map_user_id} " +
            "and ejyy_user_building.building_id=#{building_id} " +
            "and ejyy_building_info.community_id=#{community_id} " +
            "limit 0,1")
    UserBuilding selectVerify(@Param("wechat_map_user_id") Integer wechatMapUserId,@Param("building_id") Integer buildingId,@Param("community_id") Integer communityId);
}

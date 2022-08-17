package com.openlab.service.option.ower.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.option.ower.dto.Info;
import com.openlab.service.option.ower.dto.ResultInfo;
import com.openlab.service.option.ower.entity.UserBuilding;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBuildingMapper extends BaseMapper<UserBuilding> {

    @Select("select ejyy_user_building.id user_building_id," +
            "ejyy_user_building.authenticated authenticated," +
            "ejyy_user_building.authenticated_type authenticated_type," +
            "ejyy_building_info.type type," +
            "ejyy_building_info.area area," +
            "ejyy_building_info.building building," +
            "ejyy_building_info.unit unit," +
            "ejyy_building_info.number number," +
            "ejyy_building_info.id building_id " +
            "from ejyy_user_building left join ejyy_building_info on ejyy_building_info.id=ejyy_user_building.building_id " +
            "where ejyy_user_building.wechat_mp_user_id=#{owerInfoId} " +
            "and ejyy_user_building.status=#{BINDING_BUILDING} " +
            "and ejyy_building_info.community_id=#{communityId} " +
            "order by ejyy_user_building.id desc")
    List<Info> selectBuilding(@Param("owerInfoId") Long owerInfoId, @Param("BINDING_BUILDING") Integer BINDING_BUILDING, @Param("communityId") Integer communityId);

}

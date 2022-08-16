package com.openlab.statistic.mapper;

import com.openlab.statistic.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScreenMapper {
    @Select({"select count(*) from ejyy_building_info where community_id = #{communityId}"})
    int selectBuildingTotal(Long communityId);

    int selectOwerTotal(Long communityId);

    @Select({"select count(*) from ejyy_user_car where status = 1 " +
            "and building_id in (select id from ejyy_building_info where community_id = #{communityId})"})
    int selectCarTotal(Long communityId);

    @Select({"select count(*) from ejyy_pet where community_id = #{communityId}"})
    int selectPetTotal(Long communityId);

    @Select({"select count(*) from ejyy_repair where community_id = #{communityId}"})
    int selectRepairTotal(Long communityId);

    @Select({"select count(*) from ejyy_complain where community_id = #{communityId}"})
    int selectComplainTotal(Long communityId);

    @Select({"select count(*) from ejyy_move_car where community_id = #{communityId}"})
    int selectMovecarTotal(Long communityId);

    EntranceCurrentDayLog selectEntranceCurrentDayLog(Long communityId, Long dayStart, Long dayEnd);

    ElevatorCurrentDayLog selectElevatorCurrentDayLog(Long communityId, Long dayStart, Long dayEnd);

    ParkCurrentDayLog selectParkCurrentDayLog(Long communityId, Long dayStart, Long dayEnd);

    // todo
    int selectRepairCurrentDay();
    // todo
    int selectComplainCurrentDay(Long communityId, Long dayStart, Long dayEnd);

    List<WarningCurrent> selectWarningCurrent(Long communityId, Long dayStart, Long dayEnd);

    @Select({"select title, overview from ejyy_notice_to_user " +
            "where community_id = #{communityId} and published = true " +
            "order by id desc"})
    @ResultType(Notice.class)
    List<Notice> selectNotice(Long communityId);
}

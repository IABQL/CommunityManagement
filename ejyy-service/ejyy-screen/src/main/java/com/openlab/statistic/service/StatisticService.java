package com.openlab.statistic.service;

import com.openlab.statistic.entity.*;
import com.openlab.statistic.mapper.ScreenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private ScreenMapper screenMapper;

    public ScreenDetail getScreenDetail(Long communityId) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String dateTime = simpleDateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(dateTime));

        long dayStart = calendar.getTimeInMillis();
        long dayEnd = dayStart + 86400000;

        int buildingTotal = screenMapper.selectBuildingTotal(communityId);
        int owerTotal = screenMapper.selectOwerTotal(communityId);
        int carTotal = screenMapper.selectCarTotal(communityId);
        int petTotal = screenMapper.selectPetTotal(communityId);
        int repairTotal = screenMapper.selectRepairTotal(communityId);
        int complainTotal = screenMapper.selectComplainTotal(communityId);
        int movecarTotal = screenMapper.selectMovecarTotal(communityId);
        EntranceCurrentDayLog entranceCurrentDayLog = screenMapper.selectEntranceCurrentDayLog(communityId, dayStart, dayEnd);
        ElevatorCurrentDayLog elevatorCurrentDayLog = screenMapper.selectElevatorCurrentDayLog(communityId, dayStart, dayEnd);
        ParkCurrentDayLog parkCurrentDayLog = screenMapper.selectParkCurrentDayLog(communityId, dayStart, dayEnd);
        List<WarningCurrent> warningCurrent = screenMapper.selectWarningCurrent(communityId, dayStart, dayEnd);
        List<Notice> notice = screenMapper.selectNotice(communityId);

        Current current = new Current()
                .setEntrance_current_day_log(entranceCurrentDayLog)
                .setElevator_current_day_log(elevatorCurrentDayLog)
                .setPark_current_day_log(parkCurrentDayLog);

        ScreenDetail screenDetail = new ScreenDetail()
                .setBuilding_total(buildingTotal)
                .setOwer_total(owerTotal)
                .setCar_total(carTotal)
                .setPet_total(petTotal)
                .setRepair_total(repairTotal)
                .setComplain_total(complainTotal)
                .setMovecar_total(movecarTotal)
                .setCurrent(current)
                .setWarning_current(warningCurrent)
                .setNotice(notice);

        return screenDetail;
    }
}

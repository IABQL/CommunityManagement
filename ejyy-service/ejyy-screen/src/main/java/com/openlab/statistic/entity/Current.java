package com.openlab.statistic.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Current {
    private EntranceCurrentDayLog entrance_current_day_log;
    private ElevatorCurrentDayLog elevator_current_day_log;
    private ParkCurrentDayLog park_current_day_log;
}

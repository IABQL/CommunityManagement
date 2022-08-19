package com.openlab.notice.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ScreenDetail {
    private int building_total;
    private int ower_total;
    private int car_total;
    private int pet_total;
    private int repair_total;
    private int complain_total;
    private int movecar_total;
    private int cpu = 0;
    private int mem = 0;
    private int disk = 0;
    private Log log = null;
    private Iot iot = null;
    private Current current;
    private Order order = null;
    private List<WarningCurrent> warning_current;
    private Notice notice;
}

package com.openlab.statistic.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WarningCurrent {
    private Long created_at;
    private int category;
    private Long building_id;
    private int type;
    private String area;
    private String building;
    private String unit;
    private String number;
}

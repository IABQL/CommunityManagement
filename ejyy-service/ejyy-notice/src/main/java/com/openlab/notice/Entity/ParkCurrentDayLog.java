package com.openlab.notice.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ParkCurrentDayLog {
    private Long created_at;
    private String car_number;
    private String gate;
    private String name;
}

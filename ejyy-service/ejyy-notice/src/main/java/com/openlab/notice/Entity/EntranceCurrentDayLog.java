package com.openlab.notice.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EntranceCurrentDayLog {
    private Long created_at;
    private String name;
    private String real_name;
    private String vistor_name;
}

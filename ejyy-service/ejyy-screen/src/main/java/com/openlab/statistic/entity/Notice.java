package com.openlab.statistic.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Notice {
    private String title;
    private String overview;
}

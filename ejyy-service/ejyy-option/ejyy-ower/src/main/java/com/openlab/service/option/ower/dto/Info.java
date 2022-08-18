package com.openlab.service.option.ower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Info {
    private Long userBuildingId;
    private Integer authenticated=0;
    private Integer authenticatedType;
    private Integer type;
    private String area;
    private String building;
    private String unit;
    private String number;
    private Integer buildingId;
}

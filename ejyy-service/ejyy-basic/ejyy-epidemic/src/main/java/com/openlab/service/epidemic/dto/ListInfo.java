package com.openlab.service.epidemic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListInfo implements Serializable {
    private Long id;
    private Integer tour_code;
    private Float temperature;
    private Integer return_hometown;
    private Long created_at;


    private Long type;
    private String area;
    private String building;
    private String unit;
    private String number;
}

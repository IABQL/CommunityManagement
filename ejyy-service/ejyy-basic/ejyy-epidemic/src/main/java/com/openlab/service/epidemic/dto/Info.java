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
public class Info implements Serializable {
    private Long id;
    private Long building_id;
    private Integer tour_code;
    private Long wechat_mp_user_id;
    private Float temperature;
    private Integer return_hometown;
    private String return_from_province;
    private String return_from_city;
    private String return_from_district;
    private Long created_by;
    private Long created_at;
    private Integer type;
    private String area;
    private String building;
    private String unit;
    private String number;
    private String user_real_name;
    private String created_user_real_name;
}

package com.openlab.service.epidemic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Epidemic implements Serializable {
    private Long id;
    private Long wechatMpUserId;
    private Long communityId;
    private Integer tourCode;
    private Float temperature;
    private Integer returnHometown;
    private String returnFromProvince;
    private String returnFromCity;
    private String returnFromDistrict;
    private Long createdBy;
    private Long createdAt;
}

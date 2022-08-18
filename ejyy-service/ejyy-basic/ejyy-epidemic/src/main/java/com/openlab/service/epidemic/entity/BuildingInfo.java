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
public class BuildingInfo implements Serializable {
    private Integer id;
    private Long communityId;
    private Integer type;
    private String area;
    private String building;
    private String unit;
    private String number;
    private Float constructionArea;
    private Long createdBy;
    private Long createdAt;
}

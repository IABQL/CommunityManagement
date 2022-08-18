package com.openlab.service.option.ower.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBuilding implements Serializable {
    private Long id;
    private Long buildingId;
    private Long wechatMpUserId;
    private Integer authenticated;
    private Integer authenticatedType;
    private Long authenticatedUserId;
    private Integer status=1;
    private Long createdAt;
}

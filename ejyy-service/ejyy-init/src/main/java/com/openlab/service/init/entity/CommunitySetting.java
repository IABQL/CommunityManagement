package com.openlab.service.init.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 小区设置表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunitySetting implements Serializable {
    private Long id;
    private Long communityId; // 小区ID
    private boolean accessNfc; // 是否开记 NFC 功能
    private boolean accessQrcode; // 是否开启二维码
    private boolean accessRemote; // 是否开启远程开门
    private int carportMaxCar; //
    private int garageMaxCar; //
    private boolean fitmentPledge; //
}

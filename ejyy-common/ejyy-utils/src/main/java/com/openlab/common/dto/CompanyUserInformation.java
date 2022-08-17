package com.openlab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *    缴费查询到的用户部分
 *
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyUserInformation implements Serializable {
    String phone;   // 用户电话
    String idcard;  // 用户身份证
    Integer paymentType; // 缴费类型
    Double  paymentPrice; // 缴费金额

}

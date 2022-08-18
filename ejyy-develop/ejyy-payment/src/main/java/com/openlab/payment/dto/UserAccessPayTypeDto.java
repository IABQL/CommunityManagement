package com.openlab.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  /payment/type 返回结果
 *
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccessPayTypeDto implements Serializable {
    String userName;  // 用户名
    String  communityName;  // 用户所住小区名
    Double electricPaymentRemainPrice; // 剩余电费
    Double waterPaymentRemainPrice; // 剩余水费
    Double gasPaymentRemainPrice; // 剩余气费
}

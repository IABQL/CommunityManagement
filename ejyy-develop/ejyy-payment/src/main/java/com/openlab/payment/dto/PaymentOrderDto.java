package com.openlab.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *   /payment/order 返回结果
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrderDto {
    Integer id;
    String orderId;
    Integer userId;
    Long communityId;
    String communityName;
    String userName;
    String paymentType ; // 缴费类型
    Double paymentPrice;  // 缴费金额
    Long paymentTime; // 缴费时间
    Double paymentRemainPrice; // 缴费后的余额
    int orderState; // 订单状态
}

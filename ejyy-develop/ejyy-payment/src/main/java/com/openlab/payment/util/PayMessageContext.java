package com.openlab.payment.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PayMessageContext implements Serializable {
    Integer id;
    String orderId;
    Integer userId;
    Long communityId;
    String communityName;
    String userName;
    int paymentType ; // 缴费类型
    Double paymentPrice;  // 缴费金额
    Long paymentTime; // 缴费时间
    Double paymentRemainPrice; // 缴费后的余额
    int orderState; // 订单状态
    String paymentTaskType; // 消息类型（删除、保存）
}

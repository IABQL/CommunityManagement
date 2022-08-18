package com.openlab.payment.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("ejyy_property_company_user_order")
public class PaymentOrder implements Serializable {
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
}

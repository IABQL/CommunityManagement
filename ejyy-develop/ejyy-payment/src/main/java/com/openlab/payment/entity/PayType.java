package com.openlab.payment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayType {
        Integer id;
        Integer userId;  // 用户ID
        Long communityId ;  // 小区ID
        Integer usedQuantity; // 所用的水电气的数量
        Double paymentRemainPrice; // 缴费后的剩余
}

package com.openlab.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayTypeDto {
    Integer id;
    Integer user_id;  // 用户ID
    Long community_id ;  // 小区ID
    Integer used_quantity; // 所用的水电气的数量
    Double payment_remain_price; // 缴费后的剩余
}

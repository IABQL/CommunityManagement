package com.openlab.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *   前端缴费提交后的数据
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyPayment implements Serializable {
    String user_idcard; // 用户身份证
    String user_phone;  // 用户电话
    int payment_type;
    Double payment_price;
}

package com.openlab.payment.entity;

import lombok.Data;

@Data
public class AllPayTypeRemainPrice {
    Double electricPaymentRemainPrice; // 剩余电费
    Double waterPaymentRemainPrice; // 剩余水费
    Double gasPaymentRemainPrice; // 剩余气费
}

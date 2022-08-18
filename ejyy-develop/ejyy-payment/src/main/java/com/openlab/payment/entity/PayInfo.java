package com.openlab.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
*    水电气表
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayInfo {
    Integer type;
    PayType payType;
}

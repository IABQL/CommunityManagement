package com.openlab.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.payment.entity.PaymentOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderMapper extends BaseMapper<PaymentOrder> {
}

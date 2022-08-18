package com;

import com.openlab.payment.service.PaymentOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDemo {
    @Autowired
    PaymentOrderService orderService;

    @Test
    public void test(){
//        PaymentOrder paymentOrder= PaymentOrder.builder()
//                .paymentPrice(100.0)
//                .paymentTime(System.currentTimeMillis())
//                .paymentType(2)
//                .paymentRemainPrice(20.0)
//                .communityId(2L)
//                .userId(2)
//                .userName("陈")
//                .communityName("沙金村")
//                .id(null)
//                .orderId("fdafege")
//                .orderState(OrderState.DEALING.getState())
//                .build();
//        orderService.save(paymentOrder);
    }
}

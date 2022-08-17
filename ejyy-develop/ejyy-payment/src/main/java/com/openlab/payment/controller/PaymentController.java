package com.openlab.payment.controller;

import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.payment.dto.PayTypeDto;
import com.openlab.payment.dto.PaymentOrderDto;
import com.openlab.payment.entity.DailyPayment;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PayType;
import com.openlab.payment.entity.PaymentOrder;
import com.openlab.payment.exception.RepeatTimeException;
import com.openlab.payment.feign.PaymentFeign;
import com.openlab.payment.service.PayTypeService;
import com.openlab.payment.service.PaymentOrderService;
import com.openlab.payment.util.OrderState;
import com.openlab.payment.util.PayTypeEnum;
import com.openlab.payment.util.UserInfomationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RequestMapping("/payment")
@CrossOrigin
@Controller
public class PaymentController {

    @Autowired
    PaymentFeign paymentFeign;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    PayTypeService payTypeService;

    @PostMapping("/order")
    @ResponseBody
    public PaymentOrderDto CreateOrder(@RequestBody DailyPayment dailyPayment) {
        CompanyUserInformation companyUserInformation = UserInfomationUtils.getCompanyUserInformation(dailyPayment);

        // 生成半订单(没有订单状态、订单ID)
        PaymentOrder paymentOrder = paymentOrderService.createPaymentOrder(companyUserInformation);

        // 生成订单号(缴费类型 + 用户ID + 小区ID + 缴费金额)
        String orderId = paymentOrderService.getOrderId(paymentOrder);
        long currentTime = System.currentTimeMillis();
        if(!redisTemplate.hasKey(orderId)){
            redisTemplate.opsForValue().set(orderId,currentTime,10 , TimeUnit.SECONDS);
        }else{
           long preTime = (long)redisTemplate.opsForValue().get(orderId);
           if((currentTime-preTime)/1000 <= 10){
               throw new RepeatTimeException("请勿重复提交订单");
           }
        }
        // 生成完整订单(正在处理中)
        paymentOrder.setOrderId(orderId);
        paymentOrder.setOrderState(OrderState.DEALING.getState());

        // 保存订单
        paymentOrderService.save(paymentOrder);



        // 修改水电气表的金额
        PayInfo payInfo = payTypeService.queryPayType(paymentOrder.getUserId(), paymentOrder.getPaymentType());
        PayTypeDto payTypeDto = PayTypeDto.builder()
                        .payment_remain_price(payInfo.getPayType().getPaymentRemainPrice())
                                .used_quantity(payInfo.getPayType().getUsedQuantity())
                                        .user_id(payInfo.getPayType().getUserId())
                                                .community_id(payInfo.getPayType().getCommunityId())
                                                        .build();
        payTypeService.updateRemainPrice(payInfo.getType(),payTypeDto);

        // 创建订单传输对象返回到前端
        String paymentType = null;
        if(paymentOrder.getPaymentType() == PayTypeEnum.WATER.getCode()){
            paymentType = PayTypeEnum.WATER.getMessage();
        }else if(paymentOrder.getPaymentType() == PayTypeEnum.ELECTRIC.getCode()){
            paymentType = PayTypeEnum.ELECTRIC.getMessage();
        }else if(paymentOrder.getPaymentType() == PayTypeEnum.GAS.getCode()){
            paymentType = PayTypeEnum.GAS.getMessage();
        }
        PaymentOrderDto paymentOrderDto = PaymentOrderDto.builder()
                .paymentPrice(paymentOrder.getPaymentPrice())
                .paymentTime(paymentOrder.getPaymentTime())
                .paymentType(paymentType)
                .paymentRemainPrice(paymentOrder.getPaymentRemainPrice())
                .communityId(paymentOrder.getCommunityId())
                .userId(paymentOrder.getUserId())
                .userName(paymentOrder.getUserName())
                .communityName(paymentOrder.getCommunityName())
                .id(null)
                .orderId(paymentOrder.getOrderId())
                .orderState(OrderState.DEALING.getState())
                .build();
        return paymentOrderDto;
    }

    @GetMapping("/type/{id}")
    PayType getGas(@PathVariable("id") Integer id) {

         return null;
    }



}

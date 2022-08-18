package com.openlab.payment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.common.dto.AllNameInfo;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.payment.dto.*;
import com.openlab.payment.entity.*;
import com.openlab.payment.exception.RepeatTimeException;
import com.openlab.payment.feign.PaymentFeign;
import com.openlab.payment.service.PayTypeService;
import com.openlab.payment.service.PaymentOrderService;
import com.openlab.payment.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/payment")
@CrossOrigin
@RestController
public class PaymentController {

    @Autowired
    PaymentFeign paymentFeign;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    PayTypeService payTypeService;

    @Autowired
    ConsumerTask consumerTask;

    @Autowired
    PaymentTask paymentTask;

    @PostMapping("/order")
    public PaymentOrderDto CreateOrder(@RequestBody DailyPayment dailyPayment) {
        CompanyUserInformation companyUserInformation = UserInfomationUtils.getCompanyUserInformation(dailyPayment);

        // 生成半订单(没有订单状态、订单ID)
        PaymentOrder paymentOrder = paymentOrderService.createPaymentOrder(companyUserInformation);

        // 生成订单号(缴费类型 + 用户ID + 小区ID + 缴费金额)
        String orderId = paymentOrderService.createOrderId(paymentOrder);
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


        // 存储订单
       paymentOrderService.save(paymentOrder);


        // 修改水电气表的金额
        PayInfo payInfo = payTypeService.queryPayType(paymentOrder.getUserId(), paymentOrder.getPaymentType());
        PayTypeDto payTypeDto = PayTypeDto.builder()
                        .payment_remain_price(payInfo.getPayType().getPaymentRemainPrice() + paymentOrder.getPaymentPrice())
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


    @GetMapping("/user")
    UserAccessPayTypeDto getState(@RequestBody UserAccessPayType userAccessPayType){
        // 通过用户电话号码或身份证获取用户ID和小区ID
        UserAccessCommunityId userAccessCommunityId = payTypeService.getUserAccessCommunityId(userAccessPayType);

        System.out.println(userAccessCommunityId);

        // 通过用户ID和小区ID获取缴费表的信息
        AllPayTypeRemainPrice allPrice = payTypeService.getAllPrice(userAccessCommunityId);
        AllNameInfo allName = paymentFeign.getAllName(userAccessCommunityId.getUserId(),userAccessCommunityId.getCommunityId());

        return UserAccessPayTypeDto.builder()
                .communityName(allName.getCommunityName())
                .userName(allName.getUserName())
                .electricPaymentRemainPrice(allPrice.getElectricPaymentRemainPrice())
                .gasPaymentRemainPrice(allPrice.getGasPaymentRemainPrice())
                .waterPaymentRemainPrice(allPrice.getWaterPaymentRemainPrice())
                .build();
    }

    @GetMapping("/order/state")
    StateDto getState(@RequestParam("order_id") String order_id){
        return paymentOrderService.getOrderState(order_id);
    }

    @GetMapping("/orders")
    List<PaymentOrder> getPaymentOrder(@RequestBody PageContext pageContext){
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        if(pageContext.getOrder_state() != null)
          queryWrapper.eq("order_state",pageContext.getOrder_state());

        Page<PaymentOrder> page = new Page<>(pageContext.getCurrent_page(), pageContext.getPage_size());
        Page<PaymentOrder> orderPage = paymentOrderService.page(page, queryWrapper);
        return orderPage.getRecords();
    }

}

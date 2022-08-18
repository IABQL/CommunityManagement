package com.openlab.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import com.openlab.payment.dto.StateDto;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PayType;
import com.openlab.payment.entity.PaymentOrder;
import com.openlab.payment.feign.PaymentFeign;
import com.openlab.payment.mapper.PaymentOrderMapper;
import com.openlab.payment.service.PayTypeService;
import com.openlab.payment.service.PaymentOrderService;
import com.openlab.payment.util.ConsumerTask;
import com.openlab.payment.util.PaymentTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
public class PaymentOrderServiceImpl
        extends ServiceImpl<PaymentOrderMapper, PaymentOrder>
        implements PaymentOrderService {

    @Autowired
    PayTypeService payTypeService;

    @Autowired
    PaymentFeign paymentFeign;

    @Autowired
    RedisTemplate redisTemplate;

    CompanyUserPart companyUserPart;
    // 通过缴费类型返回水电气其中之一的表信息
    @Override
    public PaymentOrder createPaymentOrder(CompanyUserInformation companyUserInformation) {

        // 通过手机或者身份证查询查询到用户部分信息
        companyUserPart =
                paymentFeign.getPropertyCompanyUser(companyUserInformation);

        // 获取缴费数据
        PayInfo payInfo = payTypeService.queryPayType(companyUserPart.getId(), companyUserInformation.getPaymentType());

        // 通过缴费数据生成订单
       return createPaymentOrder(payInfo,companyUserInformation);
    }

    @Override
    public PaymentOrder createPaymentOrder(PayInfo payInfo,CompanyUserInformation companyUserInformation) {

        PayType payType = payInfo.getPayType();
        if (ObjectUtils.isEmpty(payType)) {
        // 初始化缴费表
            payType = new PayType();
            payType.setPaymentRemainPrice(0.0);
            payType.setUsedQuantity(0);
            payType.setId(null);
            payType.setUserId(companyUserPart.getId());
            payType.setCommunityId(companyUserPart.getCommunityId());

            payTypeService.savePayType(payInfo.getType(),payType);

            payInfo = PayInfo.builder()
                    .type(companyUserInformation.getPaymentType())
                    .payType(payType)
                    .build();
        }
        // 生成订单
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .id(null)
                .orderId(null)
                .userId(companyUserPart.getId())
                .communityId(companyUserPart.getCommunityId())
                .communityName(companyUserPart.getCommunityName())
                .userName(companyUserPart.getRealName())
                .paymentRemainPrice(payInfo.getPayType().getPaymentRemainPrice()+companyUserInformation.getPaymentPrice())
                .paymentTime(System.currentTimeMillis())
                .paymentType(payInfo.getType())
                .paymentPrice(companyUserInformation.getPaymentPrice())
                .build();
        return paymentOrder;
    }

    @Override
    public String createOrderId(PaymentOrder paymentOrder){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(System.currentTimeMillis());
        String orderKey = date +
                         paymentOrder.getPaymentType()+
                         paymentOrder.getUserId()+
                         paymentOrder.getCommunityId();
        return orderKey;
    }

    ExecutorService executor = Executors.newFixedThreadPool(4);

    @Override
    public void startSave(PaymentOrder paymentOrder) {

            FutureTask<Integer> futureTask = new FutureTask<>(()->{
                ConsumerTask consumerTask = new ConsumerTask();
                consumerTask.consumer();
                return 1;
            });
            FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
                PaymentTask paymentTask = new PaymentTask();
                paymentTask.produce(paymentOrder);
                return 1;
            });
            executor.execute(futureTask);
            executor.execute(futureTask2);
    }

    @Override
    public StateDto getOrderState(String orderId) {
        return baseMapper.getOrderState(orderId);
    }
}


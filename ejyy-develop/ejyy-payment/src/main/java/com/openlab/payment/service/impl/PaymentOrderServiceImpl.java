package com.openlab.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.common.dto.CompanyUserPart;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PayType;
import com.openlab.payment.entity.PaymentOrder;
import com.openlab.payment.feign.PaymentFeign;
import com.openlab.payment.mapper.PaymentOrderMapper;
import com.openlab.payment.service.PayTypeService;
import com.openlab.payment.service.PaymentOrderService;
import com.openlab.payment.util.ConsumerTask;
import com.openlab.payment.util.PaymentTask;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

        System.out.println(payInfo);
        // 通过缴费数据生成订单
       return createPaymentOrder(payInfo,companyUserInformation);
    }

    @Override
    public PaymentOrder createPaymentOrder(PayInfo payInfo,CompanyUserInformation companyUserInformation) {

        System.out.println(payInfo.getPayType());
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
    public String getOrderId(PaymentOrder paymentOrder){
        String orderKey =
                paymentOrder.getPaymentPrice() +""
                        + paymentOrder.getPaymentType()
                        + paymentOrder.getUserId()
                        + paymentOrder.getCommunityId();
        return orderKey;
    }

    @Override
    public void startSave(PaymentOrder paymentOrder) {
        ConsumerTask consumerTask = new ConsumerTask();
        PaymentTask paymentTask = new PaymentTask();

        try {
            consumerTask.consumer();
            Thread.sleep(2000);
            paymentTask.produce(paymentOrder);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


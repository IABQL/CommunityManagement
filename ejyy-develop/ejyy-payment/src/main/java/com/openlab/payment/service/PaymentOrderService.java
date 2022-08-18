package com.openlab.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.payment.dto.StateDto;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PaymentOrder;

public interface PaymentOrderService extends IService<PaymentOrder> {

    public PaymentOrder createPaymentOrder(CompanyUserInformation companyUserInformation);
    public PaymentOrder createPaymentOrder(PayInfo payInfo,CompanyUserInformation companyUserInformation);
    public String createOrderId(PaymentOrder paymentOrder);
    public void startSave(PaymentOrder paymentOrder);
    public StateDto getOrderState(String orderId);
}

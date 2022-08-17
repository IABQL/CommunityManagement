package com.openlab.payment.util;

import com.openlab.common.dto.CompanyUserInformation;
import com.openlab.payment.entity.DailyPayment;

public final class UserInfomationUtils {

    public static CompanyUserInformation getCompanyUserInformation(DailyPayment dailyPayment){
        return  CompanyUserInformation.builder()
                .phone(dailyPayment.getUser_phone())
                .idcard(dailyPayment.getUser_idcard())
                .paymentType(dailyPayment.getPayment_type())
                .paymentPrice(dailyPayment.getPayment_price())
                .build();
    }
}

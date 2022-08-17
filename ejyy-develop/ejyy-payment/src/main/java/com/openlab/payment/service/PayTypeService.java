package com.openlab.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.payment.dto.PayTypeDto;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PayType;

public interface PayTypeService extends IService<PayType> {
    PayInfo queryPayType(int userId, int type);

    void updateRemainPrice(Integer type, PayTypeDto payType);

    void savePayType(Integer type, PayType payType);
}

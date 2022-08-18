package com.openlab.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.payment.dto.PayTypeDto;
import com.openlab.payment.dto.UserAccessCommunityId;
import com.openlab.payment.entity.AllPayTypeRemainPrice;
import com.openlab.payment.entity.PayInfo;
import com.openlab.payment.entity.PayType;
import com.openlab.payment.entity.UserAccessPayType;
import com.openlab.payment.mapper.PayTypeMapper;
import com.openlab.payment.service.PayTypeService;
import com.openlab.payment.util.PayTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class PayTypeServiceImpl
        extends ServiceImpl<PayTypeMapper, PayType>
        implements PayTypeService {


    // 从水电气表中获获取数据
    @Override
    public PayInfo queryPayType(int userId, int type) {
        PayInfo payInfo = null;
        PayType payType ;
        if(type == PayTypeEnum.GAS.getCode()){
            payType = baseMapper.getGas(userId);
            payInfo = PayInfo.builder()
                    .payType(payType)
                    .type(PayTypeEnum.GAS.getCode())
                    .build();
        }else if(type == PayTypeEnum.ELECTRIC.getCode()){
            payType = baseMapper.getElectric(userId);
            payInfo = PayInfo.builder()
                    .payType(payType)
                    .type(PayTypeEnum.ELECTRIC.getCode())
                    .build();
        }else if(type == PayTypeEnum.WATER.getCode()){
            payType = baseMapper.getWater(userId);
            payInfo = PayInfo.builder()
                    .payType(payType)
                    .type(PayTypeEnum.WATER.getCode())
                    .build();
        }
        return payInfo;
    }

    @Override
    public void updateRemainPrice(Integer type, PayTypeDto payType) {
        if(type == PayTypeEnum.GAS.getCode()){
            baseMapper.updateGas(payType);
        }else if(type == PayTypeEnum.ELECTRIC.getCode()){
           baseMapper.updateElectric(payType);
        }else if(type == PayTypeEnum.WATER.getCode()){
           baseMapper.updateWater(payType);
        }
    }

    @Override
    public void savePayType(Integer type, PayType payType) {
        if(type == PayTypeEnum.GAS.getCode()){
            baseMapper.insertGas(payType);
        }else if(type == PayTypeEnum.ELECTRIC.getCode()){
            baseMapper.insertElectric(payType);
        }else if(type == PayTypeEnum.WATER.getCode()){
            baseMapper.insertWater(payType);
        }
    }

    @Override
    public AllPayTypeRemainPrice getAllPrice(UserAccessCommunityId userAccessCommunityId) {
         return baseMapper.getAllPayTypeRemainPrice(userAccessCommunityId);
    }

    @Override
    public UserAccessCommunityId getUserAccessCommunityId(UserAccessPayType userAccessPayType) {
        return baseMapper.getUserAccessCommunityId(userAccessPayType);
    }
}

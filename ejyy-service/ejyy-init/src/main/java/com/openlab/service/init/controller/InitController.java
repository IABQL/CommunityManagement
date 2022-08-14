package com.openlab.service.init.controller;

import com.openlab.common.utils.IDCardUtil;
import com.openlab.common.utils.MD5;
import com.openlab.common.utils.Result;
import com.openlab.service.init.dto.ParamDto;
import com.openlab.service.init.entity.*;
import com.openlab.service.init.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/init")
@CrossOrigin // 支持跨域
public class InitController {
    @Autowired
    private CommunityInfoService communityInfoService;
    @Autowired
    private CommunitySettingService communitySettingService;
    @Autowired
    private PropertyCompanyAuthService propertyCompanyAuthService;
    @Autowired
    private PropertyCompanyUserService propertyCompanyUserService;
    @Autowired
    private PropertyCompanyUserAccessCommunityService propertyCompanyUserAccessCommunityService;

    @ResponseBody
    @PostMapping("/run")
    public Result run(@RequestBody ParamDto paramDto) {
        // 保存个人信息
        PropertyCompanyUser propertyCompanyUser = PropertyCompanyUser.builder()
                .account(paramDto.getAccount())
                .password(MD5.encrypt(paramDto.getPassword())) // 对密码进行加密处理
                .idcard(paramDto.getIdcard())
                .realName(paramDto.getReal_name())
                .gender(IDCardUtil.getGender(paramDto.getIdcard())) // 设置性别
                .phone(paramDto.getPhone())
                .admin(true)
                .leaveOffice(false)
                .joinCompanyAt(new Date().getTime())
                .createdAt(new Date().getTime())
                .build();
        propertyCompanyUserService.save(propertyCompanyUser);

        // 保存小区信息
        CommunityInfo communityInfo = CommunityInfo.builder()
                .name(paramDto.getName())
                .banner(paramDto.getBanner())
                .province(paramDto.getProvince())
                .city(paramDto.getCity())
                .district(paramDto.getDistrict())
                .phone(paramDto.getService_phone())
                .createdAt(new Date().getTime())
                .createdBy(propertyCompanyUser.getId().longValue())
                .build();
        communityInfoService.save(communityInfo);

        // 个人和小区的中间表
        PropertyCompanyUserAccessCommunity accessCommunity = PropertyCompanyUserAccessCommunity.builder()
                .communityId(communityInfo.getId())
                .propertyCompanyUserId(propertyCompanyUser.getId().longValue())
                .build();
        propertyCompanyUserAccessCommunityService.save(accessCommunity);

        // 个人令牌和公司的中间表实体类
        PropertyCompanyAuth propertyCompanyAuth = PropertyCompanyAuth.builder()
                .propertyCompanyUserId(propertyCompanyUser.getId().longValue())
                .token(null)
                .build();
        propertyCompanyAuthService.save(propertyCompanyAuth);

        // 小区设置表
        CommunitySetting communitySetting = CommunitySetting.builder()
                .accessNfc(paramDto.isAccess_nfc())
                .accessRemote(paramDto.isAccess_remote())
                .accessQrcode(paramDto.isAccess_qrcode())
                .fitmentPledge(paramDto.isFitment_pledge())
                .carportMaxCar(paramDto.getCarport_max_car())
                .communityId(communityInfo.getId())
                .build();
        communitySettingService.save(communitySetting);

        return Result.ok();
    }
}

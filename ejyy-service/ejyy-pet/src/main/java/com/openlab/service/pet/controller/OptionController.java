package com.openlab.service.pet.controller;

import com.openlab.common.utils.Result;
import com.openlab.service.pet.dto.Buildings;
import com.openlab.service.pet.dto.OwerDto;
import com.openlab.service.pet.dto.OwerInfo;
import com.openlab.service.pet.mapper.WechatMpUserMapper;
import com.openlab.service.pet.service.WechatMpUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:04
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/option")
@CrossOrigin
@Slf4j
public class OptionController {
    @Autowired
    private WechatMpUserService wechatMpUserService;
    @Autowired
    private WechatMpUserMapper wechatMpUserMapper;
    @PostMapping("/ower")
    public Result ower(@RequestBody OwerDto owerDto){
        String phone = owerDto.getPhone();
        Integer intact = 1;
        OwerInfo owerInfo = wechatMpUserMapper.getOwerInfo(phone, intact);
        System.out.println("owerInfo = " + owerInfo);
        if(owerInfo==null){
            return Result.ok(-130,"未查询到业主信息");
        }
        // owerInfo.setReal_name("张旭");
        // owerInfo.setAvatar_url("/avatar/default.png");
        Buildings buildings = new Buildings();
        Map<String,Object> data = new HashMap<>();
        data.put("data",owerInfo);
        data.put("houses",buildings);
        return Result.ok(200,data);
    }
}

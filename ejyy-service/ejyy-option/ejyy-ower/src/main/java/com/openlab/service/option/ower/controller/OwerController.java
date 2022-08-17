package com.openlab.service.option.ower.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.openlab.common.utils.MaskPhone;
import com.openlab.common.utils.Result;
import com.openlab.common.utils.ResultCodeEnum;
import com.openlab.service.option.ower.dto.Building;
import com.openlab.service.option.ower.dto.Info;
import com.openlab.service.option.ower.dto.OwerInfo;
import com.openlab.service.option.ower.dto.ResultInfo;
import com.openlab.service.option.ower.entity.WechatMpUser;
import com.openlab.service.option.ower.mapper.WechatMpUserMapper;
import com.openlab.service.option.ower.service.UserBuildingService;
import com.openlab.service.option.ower.service.WechatMpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/option")
public class OwerController {
    private static final Integer BINDING_BUILDING=1;
    @Autowired
    private WechatMpUserService wechatMpUserMapperService;
    @Autowired
    private UserBuildingService userBuildingService;
    @PostMapping("/ower")
    public Result ower(@RequestBody HashMap<String, Object> map) {
        String phone = (String)map.get("phone");
        Integer community_id = (Integer) map.get("community_id");
        WechatMpUser wechatMpUser = wechatMpUserMapperService.getWechatMpUser(phone, true);
        if(wechatMpUser == null) {
            return Result.ok(ResultCodeEnum.QUERY_ILLEFAL.getCode(),ResultCodeEnum.QUERY_ILLEFAL.getMessage());
        }
        OwerInfo owerInfo = OwerInfo.builder()
                .id(wechatMpUser.getId())
                .real_name(wechatMpUser.getRealName())
                .phone(wechatMpUser.getPhone())
                .avatar_url(wechatMpUser.getAvatarUrl())
                .build();
        Long owerInfoId = owerInfo.getId();
        List<Info> resultInfoList = userBuildingService.findBuilding(owerInfoId, BINDING_BUILDING, community_id);
        if (resultInfoList.size() <= 0) {
            return Result.ok(ResultCodeEnum.QUERY_ILLEFAL.getCode(),"未查询到业主信息");
        }
       /* houses: [],
        carports: [],
        warehouses: [],
        merchants: [],
        garages: []*/
        Map<String,List<ResultInfo>> buildings = new HashMap<>();
        ArrayList<ResultInfo> housesList = new ArrayList<>();
        ArrayList<ResultInfo> carportsList = new ArrayList<>();
        ArrayList<ResultInfo> warehousesList = new ArrayList<>();
        ArrayList<ResultInfo> merchantsList = new ArrayList<>();
        ArrayList<ResultInfo> garagesList = new ArrayList<>();
        for (Info  info : resultInfoList) {
            ResultInfo record = ResultInfo.builder()
                    .user_building_id(info.getUserBuildingId())
                    .authenticated(info.getAuthenticated())
                    .authenticated_type(info.getAuthenticatedType())
                    .type(info.getType())
                    .area(info.getArea())
                    .building(info.getBuilding())
                    .unit(info.getUnit())
                    .number(info.getNumber())
                    .building_id(info.getBuildingId())
                    .build();
            if (record.getType() == Building.HOUSES) {
                housesList.add(record);
            }
            if (record.getType() == Building.CARPORTS) {
                carportsList.add(record);
            }
            if (record.getType() == Building.WAREHOUSES) {
                warehousesList.add(record);
            }
            if (record.getType() == Building.MERCHANTS) {
                merchantsList.add(record);
            }
            if (record.getType() == Building.GARAGES) {
                garagesList.add(record);
            }
            //System.out.println(record);
        }

        buildings.put("houses",housesList);
        buildings.put("carports",carportsList);
        buildings.put("warehouses",warehousesList);
        buildings.put("merchants",merchantsList);
        buildings.put("garages",garagesList);

        owerInfo.setPhone(MaskPhone.mask(owerInfo.getPhone()));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("owerInfo",owerInfo);
        data.put("buildings",buildings);
        return Result.ok(ResultCodeEnum.SUCCESS.getCode(), data);
    }
}

package com.openlab.service.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.openlab.common.utils.Result;
import com.openlab.service.user.dto.StatisticDto;
import com.openlab.service.user.entity.BuildingInfo;
import com.openlab.service.user.entity.Repair;
import com.openlab.service.user.service.BuildingInfoService;
import com.openlab.service.user.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class StatisticController {
    @Autowired
    private BuildingInfoService buildingInfoService;
    @Autowired
    private RepairService repairService;


    //@PostMapping("/statistic/analysis")
    @PostMapping("/analysis")
    public Result analysis(@RequestBody StatisticDto StatisticDto) {
        if (ObjectUtils.isEmpty(StatisticDto)) {
            return Result.error().message("请求参数 community_id 为空。");
        }
        // 获取小区编号
        Long community_id = StatisticDto.getCommunity_id();

        QueryWrapper<BuildingInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", community_id);
        queryWrapper.eq("type", 1);
        long house_total = buildingInfoService.count(queryWrapper);

        long house_binding_total = buildingInfoService.houseBindingTotal(community_id);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", community_id);
        queryWrapper.eq("type", 2);
        long carport_total = buildingInfoService.count(queryWrapper);

        long carport_binding_total = buildingInfoService.carportBindingTotal(community_id);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", community_id);
        queryWrapper.eq("type", 3);
        long warehouse_total = buildingInfoService.count(queryWrapper);

        long warehouse_binding_total = buildingInfoService.warehouseBindingTotal(community_id);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", community_id);
        queryWrapper.eq("type", 4);
        long merchant_total = buildingInfoService.count(queryWrapper);

        long merchant_binding_total = buildingInfoService.merchantBindingTotal(community_id);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", community_id);
        queryWrapper.eq("type", 5);
        long garage_total = buildingInfoService.count(queryWrapper);

        long garage_binding_total = buildingInfoService.garageBindingTotal(community_id);

        long ower_total = buildingInfoService.owerTotal(community_id);

        long car_total = buildingInfoService.carTotal(community_id);

        long pet_total = buildingInfoService.petTotal(community_id);

        long time = System.currentTimeMillis();

        QueryWrapper<Repair> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("created_at", "alloted_at", "disposed_at", "finished_at", "rate");
        queryWrapper1.eq("community_id", community_id);
        queryWrapper1.between("created_at", time - 1000 * 6 * 24 * 60 * 60, time);
        List<Repair> repairList = repairService.list(queryWrapper1);

        /*
        const day_start = moment()
                    .startOf('day')
                    .valueOf();
        const day_end = moment()
                    .endOf('day')
                    .valueOf();

        const complainList = await ctx.model
                    .from('ejyy_complain')
                    .where('community_id', community_id)
                    .andWhere('created_at', '>=', day_start - 1000 * 6 * 24 * 60 * 60)
                    .andWhere('created_at', '<=', day_end)
                    .select('created_at', 'alloted_at', 'disposed_at', 'finished_at', 'rate');

        const moveCarList = await ctx.model
                    .from('ejyy_move_car')
                    .where('community_id', community_id)
                    .andWhere('created_at', '>=', day_start - 1000 * 6 * 24 * 60 * 60)
                    .andWhere('created_at', '<=', day_end)
                    .select('created_at');

        const petList = await ctx.model
                    .from('ejyy_pet')
                    .where('community_id', community_id)
                    .andWhere('created_at', '>=', day_start - 1000 * 6 * 24 * 60 * 60)
                    .andWhere('created_at', '<=', day_end)
                    .select('created_at');

        const vistorList = await ctx.model
                    .from('ejyy_vistor')
                    .where('community_id', community_id)
                    .andWhere('created_at', '>=', day_start - 1000 * 6 * 24 * 60 * 60)
                    .andWhere('created_at', '<=', day_end)
                    .select('created_at');

        const noticeList = await ctx.model
                    .from('ejyy_notice_to_user')
                    .where('community_id', community_id)
                    .andWhere('created_at', '>=', day_start - 1000 * 6 * 24 * 60 * 60)
                    .andWhere('created_at', '<=', day_end)
                    .select('created_at');
            }
        };
        }*/
        Map<String, Object> data = new HashMap<>();
        data.put("house_total", house_total);
        data.put("house_binding_total", house_binding_total);
        data.put("carport_total", carport_total);
        data.put("carport_binding_total", carport_binding_total);
        data.put("warehouse_total", warehouse_total);
        data.put("warehouse_binding_total", warehouse_binding_total);
        data.put("merchant_total", merchant_total);
        data.put("merchant_binding_total", merchant_binding_total);
        data.put("garage_total", garage_total);
        data.put("garage_binding_total", garage_binding_total);
        data.put("ower_total", ower_total);
        data.put("car_total", car_total);
        data.put("pet_total", pet_total);
        data.put("repairList", repairList);
        data.put("complainList", new ArrayList<Object>());
        data.put("moveCarList", new ArrayList<Object>());
        data.put("petList", new ArrayList<Object>());
        data.put("vistorList", new ArrayList<Object>());
        data.put("noticeList", new ArrayList<Object>());

        return Result.ok(200, data);

    }
}

package com.openlab.service.epidemic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.common.utils.Result;
import com.openlab.common.utils.ResultCodeEnum;
import com.openlab.service.epidemic.dto.Info;
import com.openlab.service.epidemic.dto.ListInfo;
import com.openlab.service.epidemic.entity.UserBuilding;
import com.openlab.service.epidemic.service.EpidemicService;
import com.openlab.service.epidemic.service.UserBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/epidemic")
public class EpidemicController {
    @Autowired
    private EpidemicService epidemicService;
    @Autowired
    private UserBuildingService userBuildingService;

    @PostMapping("/list")
    public Result list(@RequestBody HashMap<String,Object> map) {

        Integer pageSize = (Integer)map.get("page_size");
        Object tourCode = map.get("tour_code");
        Integer pageNum = (Integer)map.get("page_num");
        Integer communityId = (Integer)map.get("community_id");
        Object returnHometown = map.get("return_hometown");
        Integer total = epidemicService.getTotal((Integer) communityId,(Integer) tourCode,(Integer) returnHometown);
        IPage<ListInfo> page=new Page<>(Integer.valueOf(pageNum).longValue(),Integer.valueOf(pageSize).longValue(),total);
        IPage<ListInfo> list = epidemicService.findPage(page, communityId, (Integer)tourCode, (Integer)returnHometown);
        List<ListInfo> infos = list.getRecords();
        /*for(ListInfo listInfo : infos) {
            System.out.println(listInfo);
        }*/

        HashMap<String,Object> data = new HashMap<>();
        Integer pageMount = (total/(Integer) pageSize);
        data.put("list",infos);
        data.put("total",total);
        data.put("page_amount",pageMount);
        data.put("page_num",(Integer)pageNum);
        data.put("page_size",(Integer)pageSize);

        return Result.ok(200,data);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
        String id = (String)map.get("id");
        Integer communityId = (Integer)map.get("community_id");
        Info info = epidemicService.getInfo(Long.valueOf(id), communityId);
        /*if (!info) {
            return (ctx.body = {
                    code: QUERY_ILLEFAL,
                    message: '非法查询疫情防控数据'
            });*/
        if (info == null) {
            return Result.ok(ResultCodeEnum.QUERY_ILLEFAL.getCode(), ResultCodeEnum.QUERY_ILLEFAL.getMessage());
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("info", info);

        return Result.ok(200,data);
    }

    @PostMapping("/create")
    public Result create(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
        Integer wechatMpUserId = (Integer) map.get("wechat_mp_user_id");
        //System.out.println(wechatMpUserId);
        Integer buildingId = (Integer) map.get("building_id");
        //System.out.println(buildingId);
        Integer communityId = (Integer) map.get("community_id");
        //System.out.println(communityId);
        String temperature = (String) map.get("temperature");
        Integer tourCode = (Integer) map.get("tour_code");
        Integer returnHometown = (Integer) map.get("return_hometown");
        String returnFromProvince = (String) map.get("return_from_province");
        String returnFromCity = (String) map.get("return_from_city");
        String returnFromDistrict = (String) map.get("return_from_district");
        String createdBy = (String) map.get("created_by");

        Long createdAt =  new Date().getTime();//Long.valueOf(DateUtil.getTime());
        UserBuilding verify = userBuildingService.findVerify(wechatMpUserId, buildingId, communityId);
        if (verify == null) {
            return Result.ok(ResultCodeEnum.QUERY_ILLEFAL.getCode(),"非法数据，不能作为疫情防控数据");
        }
        /*wechat_mp_user_id: number;
        building_id: number;
        community_id: number;
        temperature: number;
        tour_code: typeof GREEN_TOUR_CODE | typeof YELLOW_TOUR_CODE | typeof RED_TOUR_CODE;
        return_hometown: typeof TRUE | typeof FALSE;
        return_from_province?: string;
        return_from_city?: string;
        return_from_district: string;*/

        epidemicService.getId(wechatMpUserId, buildingId, communityId, tourCode, temperature, returnHometown, returnFromProvince, returnFromCity, returnFromDistrict, createdBy, createdAt);
        Integer id = epidemicService.getId();
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",id);
        return Result.ok(ResultCodeEnum.SUCCESS.getCode(), data);
    }
}

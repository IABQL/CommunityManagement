package com.openlab.service.epidemic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.epidemic.dto.Info;
import com.openlab.service.epidemic.dto.ListInfo;
import com.openlab.service.epidemic.entity.Epidemic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpidemicService extends IService<Epidemic> {
    IPage<ListInfo> findPage(IPage<ListInfo> page, Integer communityId, Integer tourCode,Integer returnHometown);

    Integer getTotal(Integer communityId , Integer tourCode,Integer returnHometown);

    Info getInfo(Long id,Integer community_id);

    Integer getId(Integer wechatMpUserId,
                  Integer buildingId,
                  Integer communityId,
                  Integer tourCode,
                  String temperature,
                  Integer returnHometown,
                  String returnFromProvince,
                  String returnFromCity,
                  String returnFromDistrict,
                  String createdBy,
                  Long createdAt);

    Integer getId();
}

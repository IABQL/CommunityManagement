package com.openlab.service.epidemic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.epidemic.dto.Info;
import com.openlab.service.epidemic.dto.ListInfo;
import com.openlab.service.epidemic.entity.Epidemic;
import com.openlab.service.epidemic.mapper.EpidemicMapper;
import com.openlab.service.epidemic.service.EpidemicService;
import org.springframework.stereotype.Service;

@Service
public class EpidemicServiceImpl extends ServiceImpl<EpidemicMapper, Epidemic> implements EpidemicService {
    @Override
    public IPage<ListInfo> findPage(IPage<ListInfo> page, Integer communityId ,Integer tourCode,Integer returnHometown) {
        return baseMapper.selectPageVo(page,communityId,tourCode,returnHometown);
    }

    @Override
    public Integer getTotal(Integer communityId,Integer tourCode,Integer returnHometown) {
        return baseMapper.total(communityId,tourCode,returnHometown);
    }

    @Override
    public Info getInfo(Long id, Integer community_id) {
        return baseMapper.find(id,community_id);
    }

    @Override
    public Integer getId(Integer wechatMpUserId, Integer buildingId, Integer communityId, Integer tourCode, String temperature, Integer returnHometown, String returnFromProvince, String returnFromCity, String returnFromDistrict, String createdBy, Long createdAt) {
        return baseMapper.insertGetId(wechatMpUserId, buildingId, communityId, tourCode, temperature, returnHometown, returnFromProvince, returnFromCity, returnFromDistrict, createdBy, createdAt);
    }

    @Override
    public Integer getId() {
        return baseMapper.findId();
    }
}

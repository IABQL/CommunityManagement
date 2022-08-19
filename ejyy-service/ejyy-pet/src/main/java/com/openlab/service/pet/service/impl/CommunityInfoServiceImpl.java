package com.openlab.service.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.pet.entity.CommunityInfo;
import com.openlab.service.pet.mapper.CommunityInfoMapper;
import com.openlab.service.pet.service.CommunityInfoService;
import org.springframework.stereotype.Service;

/**
 * 小区信息业务接口实现类
 */
@Service
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements CommunityInfoService {

}

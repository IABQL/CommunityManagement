package com.openlab.service.option.ower.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.option.ower.entity.WechatMpUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WechatMpUserMapper extends BaseMapper<WechatMpUser> {
    @Select("select id,real_name,phone,avatar_url from ejyy_wechat_mp_user where phone=#{phone} and intact=#{intact}")
    WechatMpUser selectInfo(@Param("phone") String phone, @Param("intact") boolean intact);
}

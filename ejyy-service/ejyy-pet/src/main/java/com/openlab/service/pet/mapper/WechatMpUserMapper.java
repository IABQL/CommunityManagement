package com.openlab.service.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.service.pet.dto.OwerInfo;
import com.openlab.service.pet.entity.WechatMpUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:52
 * \* Description:
 * \
 */
@Repository
public interface WechatMpUserMapper extends BaseMapper<WechatMpUser> {
    @Select("select id,real_name,phone,avatar_url from ejyy_wechat_mp_user where phone=#{phone} and intact=#{intact}")
    OwerInfo getOwerInfo(@Param("phone")String phone,@Param("intact")Integer intact);


}

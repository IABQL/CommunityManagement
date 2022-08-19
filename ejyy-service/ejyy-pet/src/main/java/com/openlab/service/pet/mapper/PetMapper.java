package com.openlab.service.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.openlab.service.pet.entity.Pet;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:17
 * \* Description:
 * \
 */
@Repository
public interface PetMapper extends BaseMapper<Pet> {

  /*  @Select("select ejyy_pet.id," +
            "                ejyy_pet.wechat_mp_user_id," +
            "                ejyy_pet.name," +
            "                ejyy_pet.sex," +
            "                ejyy_pet.pet_type," +
            "                ejyy_pet.coat_color," +
            "                ejyy_pet.breed," +
            "                ejyy_pet.photo," +
            "                ejyy_pet.pet_license," +
            "                ejyy_pet.pet_license_award_at," +
            "                ejyy_community_info.name as community_name," +
            "                ejyy_pet.remove," +
            "                ejyy_pet.remove_reason," +
            "                ejyy_pet.removed_at," +
            "                ejyy_wechat_mp_user.real_name " +
            "    from ejyy_pet left join ejyy_community_info on ejyy_community_info.id=ejyy_pet.community_id " +
            "    left join ejyy_wechat_mp_user on ejyy_wechat_mp_user.id=ejyy_pet.wechat_mp_user_id " +
            "    where ejyy_pet.id=#{id} or community_id=#{community_id};"
    )
    Pet info(@Param("id") Long id, @Param("community_id") Long community_id);
*/

    @Select("SELECT * FROM ejyy_pet")
    IPage<Pet> selectPage(IPage<?> page);

}

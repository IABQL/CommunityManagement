package com.openlab.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.payment.dto.PayTypeDto;
import com.openlab.payment.dto.UserAccessCommunityId;
import com.openlab.payment.entity.AllPayTypeRemainPrice;
import com.openlab.payment.entity.PayType;
import com.openlab.payment.entity.UserAccessPayType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PayTypeMapper extends BaseMapper<PayType> {
    @Select("select * from ejyy_payment_electric where user_id=#{userId}")
    PayType getElectric(@Param("userId") int userId);

    @Select("select * from ejyy_payment_gas where user_id=#{userId}")
    PayType getGas(@Param("userId") int userId);

    @Select("select * from ejyy_payment_water where user_id=#{userId}")
    PayType getWater(@Param("userId") int userId);

    @Update("<script> " +
            "UPDATE ejyy_payment_electric SET " +
            "<if test='used_quantity!=null '>" +
            " used_quantity = #{used_quantity}," +
            "</if>" +
            "<if test='payment_remain_price!=null '>" +
            "payment_remain_price = #{payment_remain_price}" +
            "</if>" +
            "<where>" +
            "<if test='user_id!=null '>" +
            "AND user_id = #{user_id} " +
            "</if>" +
            "<if test='community_id!=null '>" +
            "AND community_id = #{community_id} " +
            "</if>" +
            "</where> " +
            "</script>")
    void updateElectric(PayTypeDto payType);

    @Update("<script> " +
            "UPDATE ejyy_payment_gas SET " +
            "<if test='used_quantity!=null '>" +
            " used_quantity = #{used_quantity}," +
            "</if>" +
            "<if test='payment_remain_price!=null '>" +
            "payment_remain_price = #{payment_remain_price}" +
            "</if>" +
            "<where>" +
            "<if test='user_id!=null '>" +
            "AND user_id = #{user_id} " +
            "</if>" +
            "<if test='community_id!=null '>" +
            "AND community_id = #{community_id} " +
            "</if>" +
            "</where> " +
            "</script>")
    void updateGas(PayTypeDto payType);

    @Update("<script> " +
            "UPDATE ejyy_payment_water SET " +
            "<if test='used_quantity!=null '>" +
            " used_quantity = #{used_quantity}," +
            "</if>" +
            "<if test='payment_remain_price!=null '>" +
            "payment_remain_price = #{payment_remain_price}" +
            "</if>" +
            "<where>" +
            "<if test='user_id!=null '>" +
            "AND user_id = #{user_id} " +
            "</if>" +
            "<if test='community_id!=null '>" +
            "AND community_id = #{community_id} " +
            "</if>" +
            "</where> " +
            "</script>")
    void updateWater(PayTypeDto payType);


    @Insert("insert into ejyy_payment_electric (id,user_id,community_id,used_quantity,payment_remain_price) values " +
            "(null,#{userId},#{communityId},#{usedQuantity},#{paymentRemainPrice})")
    void insertElectric(PayType payType);
    @Insert("insert into ejyy_payment_gas (id,user_id,community_id,used_quantity,payment_remain_price) values " +
            "(null,#{userId},#{communityId},#{usedQuantity},#{paymentRemainPrice})")
    void insertGas(PayType payType);
    @Insert("insert into ejyy_payment_water (id,user_id,community_id,used_quantity,payment_remain_price) values " +
            "(null,#{userId},#{communityId},#{usedQuantity},#{paymentRemainPrice})")
    void insertWater(PayType payType);


    @Select("select " +
            "ejyy_payment_gas.payment_remain_price gasPaymentRemainPrice," +
            "ejyy_payment_electric.payment_remain_price electricPaymentRemainPrice," +
            "ejyy_payment_water.payment_remain_price waterPaymentRemainPrice " +
            "from ejyy_payment_gas join ejyy_payment_electric join ejyy_payment_water " +
            "on ejyy_payment_gas.user_id =#{userId}  and ejyy_payment_water.community_id = #{communityId} ")
    AllPayTypeRemainPrice getAllPayTypeRemainPrice(UserAccessCommunityId userAccessCommunityId);

    @Select("<script> " +
            "select " +
            "ejyy_property_company_user_access_community.property_company_user_id userId," +
            "ejyy_property_company_user_access_community.community_id communityId " +
            "from ejyy_property_company_user_access_community " +
            "where property_company_user_id = (select ejyy_property_company_user.id " +
            "from ejyy_property_company_user " +
            "where " +
            "<if test='user_idcard!=null '>" +
            "ejyy_property_company_user.idcard = #{user_idcard}" +
            "</if>"+
            "<if test='user_phone!=null '>" +
            "ejyy_property_company_user.phone = #{user_phone}" +
            "</if>"+
            ")"+
            "</script>")
    UserAccessCommunityId getUserAccessCommunityId(UserAccessPayType userAccessPayType);
}

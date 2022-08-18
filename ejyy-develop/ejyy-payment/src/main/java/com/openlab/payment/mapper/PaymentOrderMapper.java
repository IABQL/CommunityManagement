package com.openlab.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.payment.entity.PaymentOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderMapper extends BaseMapper<PaymentOrder> {

    @Select("select ejyy_property_company_user_order.order_state " +
            "from ejyy_property_company_user_order " +
            "where order_id = #{order_id}")
    Integer getOrderState(@Param("order_id") String orderId);
}

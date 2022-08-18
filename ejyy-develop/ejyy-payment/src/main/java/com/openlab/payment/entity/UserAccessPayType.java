package com.openlab.payment.entity;

import lombok.Data;

/**
 *
 *   接收前端请求水电气表信息的对象
 * */

@Data
public class UserAccessPayType {
    Integer user_id;
    Long  community_id;
    Integer payment_type;
}

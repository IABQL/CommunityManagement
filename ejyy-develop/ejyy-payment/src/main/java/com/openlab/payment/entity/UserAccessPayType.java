package com.openlab.payment.entity;

import lombok.Data;

/**
 *
 *   接收前端请求水电气表信息的对象
 * */

@Data
public class UserAccessPayType {
   String user_phone;
   String user_idcard;
}

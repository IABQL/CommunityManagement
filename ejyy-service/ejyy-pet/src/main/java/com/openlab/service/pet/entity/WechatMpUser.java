package com.openlab.service.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:49
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatMpUser implements Serializable {
    private Long id;
    private String real_name;
    private String phone;
    private String avatar_url;
    private String open_id;
    private String union_id;
    private String nick_name;
    private String idcard;
    private Integer gender;
    private String signature;
    private Integer intact;
    private Long created_at;


}

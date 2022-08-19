package com.openlab.service.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 12:47
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwerInfo  {
    private Long id;
    private String realName;
    private String phone;
    private String avatarUrl;
}

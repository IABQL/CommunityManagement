package com.openlab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装从 token 中获取的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenDto {
    private Long id;
    private String name;
}

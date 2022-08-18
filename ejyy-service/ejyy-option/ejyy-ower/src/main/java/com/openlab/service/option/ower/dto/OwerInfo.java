package com.openlab.service.option.ower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwerInfo {
    /*'id', 'real_name', 'phone', 'avatar_url'*/
    private Long id;
    private String real_name;
    private String phone;
    private String avatar_url;
}

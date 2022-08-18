package com.openlab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyUserPart implements Serializable {
    Integer id;  // 用户ID
    String realName;  // 真实姓名
    Long communityId; // 小区ID
    String communityName;  //所住小区名
}

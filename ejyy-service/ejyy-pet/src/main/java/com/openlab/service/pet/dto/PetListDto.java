package com.openlab.service.pet.dto;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 19:27
 * \* Description:
 * \
 */
@Data
public class PetListDto {
    private Integer pageNum;
    private Integer pageSize;
    private Long communityId;
    private String breed;
    private Integer license;
    private Integer sex;
    private String coatColor;
    private Integer remove;
}

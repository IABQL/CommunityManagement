package com.openlab.service.pet.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 10:38
 * \* Description:
 * \
 */
@Data
public class PetLicenseDto implements Serializable {
    private String pet_license;
    private Long pet_license_award_at;
    private Long community_id;

}

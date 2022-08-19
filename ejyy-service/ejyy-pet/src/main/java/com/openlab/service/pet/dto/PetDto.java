package com.openlab.service.pet.dto;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:37
 * \* Description:
 * \
 */
@Data
public class PetDto {
    private Integer pet_type;
    private String name;
    private Integer sex;
    private String photo;
    private String coat_color;
    private String breed;
    private boolean haveLicense;
    private String pet_license;
    private Long pet_license_award_at;

    private Long vaccinated_at;
    private String vaccine_type;

    private Long user_id;
    private Long community_id;

}







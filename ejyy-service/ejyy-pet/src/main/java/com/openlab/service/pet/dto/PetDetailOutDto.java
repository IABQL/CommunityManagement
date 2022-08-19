package com.openlab.service.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/16
 * \* Time: 21:57
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDetailOutDto implements Serializable {
    private Long id;
    private Long wechatMpUserId;
    private Long community_id;
    private Integer pet_type;
    private String name;
    private Integer sex;
    private String photo;
    private String coat_color;
    private String breed;
    private String pet_license;
    private Long pet_license_award_at;
    private Integer remove_reason;
    private Integer remove;
    private Long removed_at;
    private Long created_at;
    private String community_name;
    //修改
    private String vaccine_type;
    private Long vaccinated_at;


}

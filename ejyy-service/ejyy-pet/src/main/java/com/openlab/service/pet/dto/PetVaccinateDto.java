package com.openlab.service.pet.dto;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 11:20
 * \* Description:
 * \
 */
@Data
public class PetVaccinateDto {
    private Long id;
    private Long vaccinated_at;
    private String vaccine_type;

}

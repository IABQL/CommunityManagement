package com.openlab.service.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 16:17
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetVaccinate implements Serializable {
    private Long id;
    private Long petId;
    private Long vaccinatedAt;
    private String vaccineType;
    private Long createdAt;
}

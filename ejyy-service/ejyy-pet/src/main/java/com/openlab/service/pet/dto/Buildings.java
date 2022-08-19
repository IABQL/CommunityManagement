package com.openlab.service.pet.dto;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/17
 * \* Time: 13:10
 * \* Description:
 * \
 */
@Data
public class Buildings {
/*   houses: [],
            carports: [],
            warehouses: [],
            merchants: [],
            garages: []*/

    private int[] houses;
    private int[] carports;
    private int[] warehouses;
    private int[] merchats;
    private int[] garages;

}

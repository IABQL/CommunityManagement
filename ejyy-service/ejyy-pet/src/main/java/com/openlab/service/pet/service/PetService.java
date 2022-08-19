package com.openlab.service.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.service.pet.entity.Pet;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:18
 * \* Description:
 * \
 */
public interface PetService extends IService<Pet> {

    /**
     * 分查询用户数据
     * @param current 当前页码
     * @param size 每页显示的记录数
     * @return 返回分页查询的数据
     */
    Page<Pet> listByPage(long current, long size);

}

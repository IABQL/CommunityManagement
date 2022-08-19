package com.openlab.service.pet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.service.pet.entity.Pet;
import com.openlab.service.pet.mapper.PetMapper;
import com.openlab.service.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:19
 * \* Description:
 * \
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {
    @Autowired
    private PetMapper petMapper;

    /**
     * 分查询用户数据
     * @param current 当前页码
     * @param size 每页显示的记录数
     * @return 返回分页查询的数据
     */
    public Page<Pet> listByPage(long current, long size) {
        Page<Pet> page = new Page<>(current, size);
        petMapper.selectPage(page);
        return page;
    }


}

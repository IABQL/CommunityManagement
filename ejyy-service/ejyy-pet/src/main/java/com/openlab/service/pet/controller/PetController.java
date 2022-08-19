package com.openlab.service.pet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.openlab.common.utils.Result;
import com.openlab.service.pet.dto.*;
import com.openlab.service.pet.entity.CommunityInfo;
import com.openlab.service.pet.entity.Pet;
import com.openlab.service.pet.entity.PetVaccinate;
import com.openlab.service.pet.mapper.CommunityInfoMapper;
import com.openlab.service.pet.mapper.PetMapper;
import com.openlab.service.pet.mapper.PetVaccinateMapper;
import com.openlab.service.pet.service.PetService;
import com.openlab.service.pet.service.PetVaccinateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:24
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/pet")
@CrossOrigin
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private PetMapper petMapper;
    @Autowired
    private PetVaccinateMapper petVaccinateMapper;
    @Autowired
    private CommunityInfoMapper communityInfoMapper;
    @Autowired
    private PetVaccinateService petVaccinateService;

    @PostMapping("/detail")
    public Result detail(@RequestBody PetDetailDto petDetailDto) {
        if (ObjectUtils.isEmpty(petDetailDto)) {
            return Result.error().message("请求参数为空。");
        }
        Long id = petDetailDto.getId();
        Long community_id = petDetailDto.getCommunity_id();
        Map<String, Object> data = new HashMap<>();
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "wechat_mp_user_id", "community_id", "pet_type", "name", "sex", "photo", "coat_color", "breed", "pet_license", "pet_license_award_at", "remove", "remove_reason", "removed_at", "created_at")
                .eq("id", id);
        Pet pet = petMapper.selectOne(queryWrapper);
        //查询小区名字
        QueryWrapper<CommunityInfo> communityInfoQueryWrapper = new QueryWrapper<>();
        communityInfoQueryWrapper.select("name").eq("id", community_id);
        CommunityInfo communityInfo = communityInfoMapper.selectOne(communityInfoQueryWrapper);
        //查询wechatuser
        //查询疫苗信息
        QueryWrapper<PetVaccinate> petVaccinateQueryWrapper = new QueryWrapper<>();
        Long pet_id = id;
        petVaccinateQueryWrapper.eq("pet_id", pet_id);
        List<PetVaccinate> petVaccinates = petVaccinateMapper.selectList(petVaccinateQueryWrapper);
        //封装数据
        PetDetailOutDto petDetailOutDto = PetDetailOutDto.builder()
                .id(pet.getId())
                .wechatMpUserId(pet.getWechatMpUserId())
                .community_id(pet.getCommunityId())
                .pet_type(pet.getPetType())
                .name(pet.getName())
                .sex(pet.getSex())
                .photo(pet.getPhoto())
                .coat_color(pet.getCoatColor())
                .breed(pet.getBreed())
                .pet_license(pet.getPetLicense())
                .pet_license_award_at(pet.getPetLicenseAwardAt())
                .remove(pet.getRemove())
                .remove_reason(pet.getRemoveReason())
                .removed_at(pet.getRemovedAt())
                .created_at(pet.getCreatedAt())
                .community_name(communityInfo.getName())
                .build();
        data.put("info", petDetailOutDto);
        data.put("vaccinates", petVaccinates);
        return Result.ok(200, data);
    }

    @PostMapping("/create")
    public Result create(@RequestBody PetDto petDto) {
        //传来的是空值
        if (ObjectUtils.isEmpty(petDto)) {
            return Result.error().message("请求参数为空。");
        }

        //保存宠物信息
        Pet pet = Pet.builder()
                .wechatMpUserId(1L)
                .communityId(petDto.getCommunity_id())
                .petType(petDto.getPet_type())
                .name(petDto.getName())
                .sex(petDto.getSex())
                .photo(petDto.getPhoto())
                .coatColor(petDto.getCoat_color())
                .breed(petDto.getBreed())
                .createdAt(System.currentTimeMillis())
                .build();
        int id = petMapper.insert(pet);

        if(petDto.isHaveLicense()){
            //更新宠物信息
            UpdateWrapper<Pet> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("pet_license", petDto.getPet_license());
            updateWrapper.set("pet_license_award_at", petDto.getPet_license_award_at());
            updateWrapper.eq("id",pet.getId());
            petMapper.update(new Pet(),updateWrapper);

            //插入宠物疫苗信息
            PetVaccinate petVaccinate = PetVaccinate.builder()
                    .petId(pet.getId())
                    .vaccinatedAt(petDto.getVaccinated_at())
                    .vaccineType(petDto.getVaccine_type())
                    .createdAt(System.currentTimeMillis())
                    .build();
            petVaccinateMapper.insert(petVaccinate);
/*            Map<String,Object> vaccine = new HashMap<>();
            vaccine.put("id",pet.getId());
            vaccine.put("vaccine_type",petDto.getVaccine_type());
            vaccine.put("vaccineted_at",petDto.getVaccinated_at());*/

        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",pet.getId()) ;
        return Result.ok(200,data);
    }

    @PostMapping("/list")
    public Result list(@RequestBody PetListDto petListDto) {
        //得到所有传来的值
        Integer page = petListDto.getPageNum();
        Integer pagesize = petListDto.getPageSize();
        Integer license = petListDto.getLicense();
        Long community = petListDto.getCommunityId();
        String breed = petListDto.getBreed();
        Integer sex = petListDto.getSex();
        String coatColor = petListDto.getCoatColor();
        Integer remove = petListDto.getRemove();

        //传来的是空值
        if (ObjectUtils.isEmpty(petListDto)) {
            return Result.error().message("请求参数为空。");
        }
                List<Pet> list = new ArrayList<>();
                long total = 0;
                //查询全部
                list = petService.list();
                total = list.size();


             Map<String, Object> data = new HashMap<>();
             QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
             queryWrapper.select("id", "name", "sex", "pet_type", "coat_color", "breed", "pet_license", "remove", "created_at");
             //如果给的breed有值通过breed查询
             if (petListDto.getBreed() != null) {
                 queryWrapper.eq("breed", breed);
             }
             //性别
             if (petListDto.getSex() != null) {
                 queryWrapper.eq("sex", sex);
             }
             //登记
            if(license!=null){
                if(license==1){
                    queryWrapper.isNotNull("pet_license");
                }else{
                    queryWrapper.isNull("pet_license");
                }
            }

             //毛色
             if (petListDto.getCoatColor() != null) {
                 queryWrapper.eq("coat_color", coatColor);
             }
             if (petListDto.getRemove() != null) {
                 queryWrapper.eq("remove", remove);
             }

            List pets = petMapper.selectList(queryWrapper);
             data.put("list", pets);
             data.put("total",total);
             return Result.ok(200, data);

    }
    //详细更新登记
    @PostMapping("/license/{id}")
    public Result license(@PathVariable("id") Long id,@RequestBody PetLicenseDto petLicenseDto){
        String pet_license = petLicenseDto.getPet_license();
        Long pet_license_award_at = petLicenseDto.getPet_license_award_at();
        Long community_id = petLicenseDto.getCommunity_id();
        UpdateWrapper<Pet> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pet_license",pet_license).set("pet_license_award_at",pet_license_award_at).eq("community_id",community_id).eq("id",id);
        int affact = petMapper.update(new Pet(),updateWrapper);
        System.out.println("affact = " + affact);
        return Result.ok();
    }
    //疫苗信息
    @PostMapping("/vaccinate/{id}")
    public Result vaccinate(@PathVariable("id") Long id,@RequestBody PetVaccinateDto petVaccinateDto){
        Long vaccinated_at = petVaccinateDto.getVaccinated_at();
        Long pet_id = petVaccinateDto.getId();
        String vaccine_type = petVaccinateDto.getVaccine_type();

        PetVaccinate petVaccinate = PetVaccinate.builder()
                .petId(id)
                .vaccinatedAt(vaccinated_at)
                .vaccineType(vaccine_type)
                .createdAt(System.currentTimeMillis())
                .build();
        int aid = petVaccinateMapper.insert(petVaccinate);
        System.out.println("疫苗类型："+vaccine_type);
/*        System.out.println("aid = " + aid);
        Map<String,Object> data = new HashMap<>();
        data.put("id",id);*/
        return Result.ok();
    }

}

package com.openlab.service.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.openlab.service.epidemic.dto.Info;
import com.openlab.service.epidemic.dto.ListInfo;
import com.openlab.service.epidemic.entity.Epidemic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface EpidemicMapper extends BaseMapper<Epidemic> {
    @Select("<script> select SQL_CALC_FOUND_ROWS ejyy_epidemic.id id," +
            "            ejyy_epidemic.tour_code tour_code," +
            "            ejyy_epidemic.temperature temperature," +
            "            ejyy_epidemic.return_hometown return_hometown," +
            "            ejyy_epidemic.created_at created_at," +
            "            ejyy_building_info.type type," +
            "            ejyy_building_info.area area," +
            "            ejyy_building_info.building building," +
            "    ejyy_building_info.unit unit," +
            "    ejyy_building_info.number number " +
            "    from ejyy_epidemic  left join ejyy_building_info on ejyy_building_info.id=ejyy_epidemic.building_id " +
            "    where ejyy_epidemic.community_id=#{communityId} <when test = 'tourCode != null'> and ejyy_epidemic.tour_code = #{tourCode} </when>" +
            "    <when test = 'returnHometown != null'> and ejyy_epidemic.return_hometown = #{returnHometown} </when>" +
            "ORDER BY  ejyy_epidemic.id desc </script>")
    IPage<ListInfo> selectPageVo(IPage<ListInfo> page, @Param("communityId") Integer communityId,@Param("tourCode") Integer tourCode,@Param("returnHometown") Integer returnHometown);

    @Select("<script> select count(*) from ejyy_epidemic" +
                   "  left join ejyy_building_info on ejyy_building_info.id=ejyy_epidemic.building_id " +
            "    where ejyy_epidemic.community_id=#{communityId} <when test = 'tourCode != null'> and ejyy_epidemic.tour_code = #{tourCode} </when>" +
            "    <when test = 'returnHometown != null'> and ejyy_epidemic.return_hometown = #{returnHometown} </when>" +
            "ORDER BY  ejyy_epidemic.id desc </script>")
    Integer total(@Param("communityId") Integer communityId,@Param("tourCode") Integer tourCode,@Param("returnHometown") Integer returnHometown);

    //查询详细信息
    @Select("select ejyy_epidemic.id, " +
           "ejyy_epidemic.building_id, " +
           "ejyy_epidemic.tour_code, " +
           "ejyy_epidemic.wechat_mp_user_id, " +
           "ejyy_epidemic.temperature, " +
           "ejyy_epidemic.return_hometown, " +
           "ejyy_epidemic.return_from_province, " +
           "ejyy_epidemic.return_from_city, " +
           "ejyy_epidemic.return_from_district, " +
           "ejyy_epidemic.created_by, " +
           "ejyy_epidemic.created_at, " +
           "ejyy_building_info.type, " +
           "ejyy_building_info.area, " +
           "ejyy_building_info.building, " +
           "ejyy_building_info.unit, " +
           "ejyy_building_info.number, " +
           "ejyy_wechat_mp_user.real_name as user_real_name, " +
           "ejyy_property_company_user.real_name as created_user_real_name " +
           "from ejyy_epidemic left join ejyy_building_info on ejyy_building_info.id=ejyy_epidemic.building_id " +
           "left join ejyy_wechat_mp_user on ejyy_wechat_mp_user.id=ejyy_epidemic.wechat_mp_user_id " +
           "left join ejyy_property_company_user on ejyy_property_company_user.id=ejyy_epidemic.created_by " +
           "where ejyy_epidemic.id=#{id} and ejyy_epidemic.community_id=#{community_id} ")
    Info find(@Param("id")Long id, @Param("community_id")Integer community_id);

    @Insert("insert into ejyy_epidemic " +
            "(wechat_mp_user_id,building_id,community_id,tour_code,temperature,return_hometown," +
            "return_from_province,return_from_city,return_from_district,created_by,created_at) " +
            "values (#{wechat_mp_user_id},#{building_id},#{community_id},#{tour_code},#{temperature},#{return_hometown}," +
            "#{return_from_province},#{return_from_city},#{return_from_district},#{created_by},#{created_at})")
    Integer insertGetId(@Param("wechat_mp_user_id") Integer wechatMpUserId,@Param("building_id") Integer buildingId,
                        @Param("community_id") Integer communityId,@Param("tour_code") Integer tourCode,
                        @Param("temperature") String temperature,@Param("return_hometown") Integer returnHometown,
                        @Param("return_from_province") String returnFromProvince,@Param("return_from_city") String returnFromCity,
                        @Param("return_from_district") String returnFromDistrict,@Param("created_by") String createdBy,
                        @Param("created_at") Long createdAt);
    @Select("SELECT LAST_INSERT_ID()")
    Integer findId();

}

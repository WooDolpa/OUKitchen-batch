package com.project.toy.batch.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/28
 */
@Mapper
public interface PlaceRepository {

    @Insert("<script>                                                                                                           "
            +"insert into /* PlaceRepository.insertPlace */ place_info(                                                         "
            +"pi_id, pi_name, pi_category_name, pi_category_group_code, pi_category_group_name, pi_phone,                       "
            +"pi_address_name, pi_road_address_name, pi_x, pi_y, pi_place_url, distance, reg_datetime)values(                   "
            +"#{piId}, #{piName}, #{piCategoryName}, #{piCategoryGroupCode}, #{piCategoryGroupName}, #{piPhone},                "
            +"#{piAddressName}, #{piRoadAddressName}, #{piX}, #{piY}, #{piPlaceUrl}, #{distance}, now()                         "
            +") on duplicate key update                                                                                         "
            +"<trim suffixOverrides=\",\">                                                                                      "
            +"<if test=\" piName != null \">                   pi_name = #{piName},                                 </if>       "
            +"<if test=\" piCategoryName != null \">           pi_category_name = #{piCategoryName},                </if>       "
            +"<if test=\" piCategoryGroupCode != null \">      pi_category_group_code = #{piCategoryGroupCode},     </if>       "
            +"<if test=\" piCategoryGroupName != null \">      pi_category_group_name = #{piCategoryGroupName},     </if>       "
            +"<if test=\" piPhone != null \">                  pi_phone = #{piPhone},                               </if>       "
            +"<if test=\" piAddressName != null \">            pi_address_name = #{piAddressName},                  </if>       "
            +"<if test=\" piRoadAddressName != null \">        pi_road_address_name = #{piRoadAddressName},         </if>       "
            +"<if test=\" piX != null \">                      pi_x = #{piX},                                       </if>       "
            +"<if test=\" piY != null \">                      pi_y = #{piY},                                       </if>       "
            +"<if test=\" piPlaceUrl != null \">               pi_road_address_name = #{piPlaceUrl},                </if>       "
            +"<if test=\" distance != null \">                 pi_road_address_name = #{distance},                  </if>       "
            +"upd_datetime = now()                                                                                              "
            +"</trim>                                                                                                           "
            +"</script>                                                                                                         "
    )
    int insertPlace (Map<String, Object> map);

}

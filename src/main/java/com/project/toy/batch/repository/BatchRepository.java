package com.project.toy.batch.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Mapper
public interface BatchRepository {

    @Insert("<script>                                                           "
            +"insert into /* BatchRepository.insertBatchTime */ batch_time(     "
            +"batch_name, last_process_time) values(                            "
            +"#{batchName},#{lastProcessTime}                                   "
            +") on duplicate key update                                         "
            +"<trim suffixOverrides=\",\">                                      "
            +"<if test=\"lastProcessTime != null and lastProcessTime != '' \">  "
            +" last_process_time = #{lastProcessTime}                           "
            +"</if>                                                             "
            +"</trim>                                                           "
            +"</script>                                                         "
    )
    int insertBatchTime (Map<String, Object> map);

    @Select("<script>                                                           "
            +"select /* BatchRepository.findBatchTime */                        "
            +"last_process_time                                                 "
            +"from batch_time                                                   "
            +"<where>                                                           "
            +"<if test=\"batchName != null \">                                  "
            +" batch_name = #{batchName}                                        "
            +"</if>                                                             "
            +"</where>                                                          "
            +"</script>                                                         "
    )
    String findBatchTime (Map<String, Object> map);

}

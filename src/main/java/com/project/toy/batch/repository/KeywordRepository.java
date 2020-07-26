package com.project.toy.batch.repository;

import com.project.toy.common.model.KeywordModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Mapper
public interface KeywordRepository {

    @Select("<script>                                                       "
            +"select /* KeywordRepository.findKeywordListTotalCount */      "
            +"count(1)                                                      "
            +"from keyword                                                  "
            +"</script>                                                     "
    )
    int findKeywordListTotalCount ();

    @Select("<script>                                                       "
            +"select /* KeywordRepository.findKeywordList */                "
            +"*                                                             "
            +"from keyword                                                  "
            +"</script>                                                     "
    )
    List<KeywordModel> findKeywordList ();

}

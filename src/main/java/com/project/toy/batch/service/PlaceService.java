package com.project.toy.batch.service;

import com.project.toy.batch.constant.BatchConstants;
import com.project.toy.batch.dto.KakaoDto;
import com.project.toy.batch.repository.KeywordRepository;
import com.project.toy.common.model.KeywordModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Service(BatchConstants.SPRING_PLACE_SERVICE_NAME)
@Slf4j
public class PlaceService {

    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private KakaoService kakaoService;

    public void process () {

        int totalCount = keywordRepository.findKeywordListTotalCount();

        if(totalCount > 0){

            Optional<List<KeywordModel>> keywordModelListOptional = Optional.ofNullable(keywordRepository.findKeywordList());

            if(keywordModelListOptional.isPresent()){

                List<KeywordModel> keywordModelList = keywordModelListOptional.get();

                for(KeywordModel keywordModel : keywordModelList){
                    // 해당 키워드 kakao map Api
                    KakaoDto.KeywordPlaceReqDto keywordPlaceReqDto = new KakaoDto.KeywordPlaceReqDto();
                    keywordPlaceReqDto.setQuery(keywordModel.getKName());
                    kakaoService.sendLocalKeyword(keywordPlaceReqDto);
                }

            }else{
                log.warn("PlaceActor|process|KeywordList|Null");
            }
        }else{
            log.info("PlaceActor|process|Keyword|Count|{}", totalCount);
        }
    }

}

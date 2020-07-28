package com.project.toy.batch.service;

import com.project.toy.batch.constant.BatchConstants;
import com.project.toy.batch.dto.KakaoDto;
import com.project.toy.batch.repository.KeywordRepository;
import com.project.toy.batch.repository.PlaceRepository;
import com.project.toy.common.model.KeywordModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private PlaceRepository placeRepository;

    @Transactional
    public int insertPlace(final KakaoDto.DocumentsDto dto) {

        int result = 0;

        try {

            Map<String, Object> map = new HashMap<>();

            map.put("piId", dto.getId());
            map.put("piName", dto.getPlaceName());
            map.put("piCategoryName", dto.getCategoryName());
            map.put("piCategoryGroupCode", dto.getCategoryGroupCode());
            map.put("piCategoryGroupName", dto.getCategoryGroupName());
            map.put("piPhone", dto.getPhone());
            map.put("piAddressName", dto.getAddressName());
            map.put("piRoadAddressName", dto.getRoadAddressName());
            map.put("piX", dto.getX());
            map.put("piY", dto.getY());
            map.put("piPlaceUrl", dto.getPlaceUrl());
            map.put("distance", dto.getDistance());

            result = placeRepository.insertPlace(map);

        }catch (Exception e){
            log.warn("PlaceActor|process|Place|Insert|Error|{}", e.getMessage());
        }

        return result;
    }

    public void process () {

        int totalCount = keywordRepository.findKeywordListTotalCount();

        if(totalCount > 0){

            Optional<List<KeywordModel>> keywordModelListOptional = Optional.ofNullable(keywordRepository.findKeywordList());

            if(keywordModelListOptional.isPresent()){

                List<KeywordModel> keywordModelList = keywordModelListOptional.get();

                for(KeywordModel keywordModel : keywordModelList){

                    boolean isEnd = false;
                    int pageNo = 1;

                    while (!isEnd) { // 마지막 페이지까지 실행

                        KakaoDto.KeywordPlaceReqDto keywordPlaceReqDto = new KakaoDto.KeywordPlaceReqDto();

                        keywordPlaceReqDto.setQuery(keywordModel.getKName());
                        keywordPlaceReqDto.setPage(pageNo);

                        Optional<KakaoDto.KeywordPlaceResDto> keywordPlaceResDtoOptional = kakaoService.sendLocalKeyword(keywordPlaceReqDto);

                        if(keywordPlaceResDtoOptional.isPresent()){ // 데이터 존재

                            KakaoDto.KeywordPlaceResDto keywordPlaceResDto = keywordPlaceResDtoOptional.get();
                            List<KakaoDto.DocumentsDto> documentsDtoList = keywordPlaceResDto.getDocuments();

                            for(KakaoDto.DocumentsDto documentsDto : documentsDtoList){
                                // 음식점인 항목 제거
                                if(!BatchConstants.CATEGORY_GROUP_CODE_RESTAURANT.equals(documentsDto.getCategoryGroupCode())){
                                    this.insertPlace(documentsDto);
                                }
                            }

                            isEnd = keywordPlaceResDto.getMeta().isEnd();
                            pageNo++;

                        }else{
                            log.warn("PlaceActor|process|keywordPlaceResDtoOptional|null");
                            break;
                        }
                    }
                }

            }else{
                log.warn("PlaceActor|process|KeywordList|Null");
            }
        }else{
            log.info("PlaceActor|process|Keyword|Count|{}", totalCount);
        }
    }

}

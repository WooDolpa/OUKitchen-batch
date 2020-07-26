package com.project.toy.batch.service;

import com.project.toy.batch.constant.BatchConstants;
import com.project.toy.batch.repository.BatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Service(BatchConstants.SPRING_BATCH_SERVICE_NAME)
@Slf4j
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    /**
     * 배치 종료시 마지막 배치 실행시간 업데이트 (해당 배치 정보가 없으면 등록)
     *
     * @param batchName
     * @param lastProcessTime
     * @return
     */
    @Transactional
    public int insertBatchTime (final String batchName, final String lastProcessTime){

        Map<String, Object> map = new HashMap<>();
        map.put("batchName", batchName);
        map.put("lastProcessTime", lastProcessTime);

        return batchRepository.insertBatchTime(map);
    }

    /**
     * 마지막 배치 실행시간 가져오기
     *
     * @param batchName
     * @return
     */
    public Optional<String> findBatchTime (final String batchName){

        Map<String, Object> map = new HashMap<>();
        map.put("batchName", batchName);

        return Optional.ofNullable(batchRepository.findBatchTime(map));
    }

}

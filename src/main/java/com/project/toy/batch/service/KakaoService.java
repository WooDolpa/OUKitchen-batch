package com.project.toy.batch.service;

import com.project.toy.batch.constant.BatchConstants;
import com.project.toy.batch.dto.KakaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Service(BatchConstants.SPRING_KAKAO_SERVICE_NAME)
@Slf4j
public class KakaoService {

    @Value("${kakao.api.key}")
    private String apiKey;
    @Value("${kakao.api.url}")
    private String apiUrl;

    public void sendLocalKeyword (final KakaoDto.KeywordPlaceReqDto dto){

        HttpsURLConnection conn = null;
        BufferedReader br = null;
        KakaoDto.KeywordPlaceResDto keywordPlaceResDto = null;

        int responseCode = 400;
        int page = 1;

        try {

            StringBuilder sb = new StringBuilder();
            String line;

            String urlString  = apiUrl+"?query="+ URLEncoder.encode(dto.getQuery(),"UTF-8")+"&page="+page;
            URL url = new URL(urlString);

            conn =  (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            /** HTTPS Header */
            conn.setRequestProperty("Authorization", "KakaoAK "+apiKey);
            /** HTTPS Configuration */
            conn.setConnectTimeout(BatchConstants.KAKAO_API_CONNECTION_TIMEOUT);
            conn.setReadTimeout(BatchConstants.KAKAO_API_READ_TIMEOUT);

            responseCode = conn.getResponseCode();

            if (responseCode >= 200 && responseCode < 400) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                keywordPlaceResDto = objectMapper.readValue(sb.toString(), KakaoDto.KeywordPlaceResDto.class);

            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
            }

        }catch (IOException ioe) {
            log.warn("sendLocalKeyword|IOException|{}", ioe.getMessage());
        } catch (Exception ex) {
            log.warn("sendLocalKeyword|Exception|{}", ex.getMessage());
        }finally {

            if(keywordPlaceResDto != null){
                log.info("keywordPlaceResDto|Meta|{}", keywordPlaceResDto.getMeta());
                for(KakaoDto.DocumentsDto documentsDto : keywordPlaceResDto.getDocuments())
                log.info("keywordPlaceResDto|Documents|{}", documentsDto);
            }

        }
    }

}

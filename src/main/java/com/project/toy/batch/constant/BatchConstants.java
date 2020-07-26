package com.project.toy.batch.constant;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
public class BatchConstants {

    // Actor Name
    public final static String ACTOR_SYSTEM_NAME                =       "batchActorSystem";
    public final static String ACTOR_PLACE_NAME                 =       "placeActor";

    // Actor Message
    public final static String ACTOR_MSG_STOP_APPLICATION       =       "stopApplication";
    public final static String ACTOR_MSG_PLACE_ACTOR_START      =       "placeActorStart";

    // Interval
    public final static int PLACE_INTERVAL_DAY                  =       1;

    // Spring Bean Name
    public final static String SPRING_BATCH_SERVICE_NAME        =       "batchService";
    public final static String SPRING_PLACE_SERVICE_NAME        =       "placeService";
    public final static String SPRING_KAKAO_SERVICE_NAME        =       "kakaoService";

    // Date Format
    public final static String DEFAULT_DATE_FORMAT              =       "yyyy-MM-dd HH:mm:ss";

    // HttpsConnection Configuration
    public final static int KAKAO_API_CONNECTION_TIMEOUT        =       3 * 1000;
    public final static int KAKAO_API_READ_TIMEOUT              =       3 * 1000;
}

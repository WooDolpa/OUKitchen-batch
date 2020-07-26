package com.project.toy.batch.actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.project.toy.batch.config.StaticApplicationContext;
import com.project.toy.batch.constant.BatchConstants;
import com.project.toy.batch.service.BatchService;
import com.project.toy.batch.service.PlaceService;
import com.project.toy.batch.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Slf4j
public class PlaceActor extends AbstractActor {

    private Receive initial;
    private Receive running;

    private BatchService batchService;
    private PlaceService placeService;

    public PlaceActor() {

        initial = this.createInitialReceive();
        running = this.createRunningReceive();

        batchService = (BatchService) StaticApplicationContext.getContext().getBean(BatchConstants.SPRING_BATCH_SERVICE_NAME);
        placeService = (PlaceService) StaticApplicationContext.getContext().getBean(BatchConstants.SPRING_PLACE_SERVICE_NAME);

        getContext().become(initial);
    }

    private Receive createInitialReceive(){
        return ReceiveBuilder
                .create()
                .match(String.class, p ->{
                    switch (p){
                        case BatchConstants.ACTOR_MSG_STOP_APPLICATION:
                            log.info("Application will be stopped because it has received stop message");
                            Thread.sleep(3000);
                            System.exit(0);
                            break;
                        case BatchConstants.ACTOR_MSG_PLACE_ACTOR_START:

                            getContext().become(running);           // 실행중 상태로 변경

                            try {
                                placeService.process();
                            }catch (Exception e){
                                log.warn("PlaceActor|Error|Exception|{}", e.getMessage());
                            }finally {
                                batchService.insertBatchTime(BatchConstants.ACTOR_PLACE_NAME, DateUtil.getTime(BatchConstants.DEFAULT_DATE_FORMAT)); // 마지막 배치 작업 시간 업데이트
                                getContext().become(initial);   // 다음 배치 실행을 위한 init 상태로 변경
                            }

                            break;
                        default:
                            log.warn("unknown message|{}", p);
                            break;
                    }
                })
                .matchAny(p -> log.warn("received unknown message..Match Any|[{}]", p.toString()))
                .build();
    }

    private Receive createRunningReceive(){
        return ReceiveBuilder.create()
                .match(String.class, p -> {
                    switch(p){
                        case BatchConstants.ACTOR_MSG_PLACE_ACTOR_START:
                            log.debug("PlaceActor has been already processing");
                            break;
                        default:
                            log.warn("received unknown String message. running: [{}]", p);
                            break;
                    }
                })
                .build();
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}

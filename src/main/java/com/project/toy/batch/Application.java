package com.project.toy.batch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import com.project.toy.batch.actor.PlaceActor;
import com.project.toy.batch.constant.BatchConstants;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Application.batchSystemStart();
    }


    /**
     * 배치 시스템
     *
     */
    public static void batchSystemStart(){

        // Akka Config
        Config config = ConfigFactory.load();
        // Akka System Create
        ActorSystem actorSystem = ActorSystem.create(BatchConstants.ACTOR_SYSTEM_NAME, config);
        // 공유 주방 등록을 처리하는 Actor
        ActorRef placeActor = actorSystem.actorOf(Props.create(PlaceActor.class), BatchConstants.ACTOR_PLACE_NAME);
        Cancellable placeActorCancellable = actorSystem.scheduler().schedule(Duration.Zero(), Duration.create(BatchConstants.PLACE_INTERVAL_HOUR, TimeUnit.HOURS), placeActor, BatchConstants.ACTOR_MSG_PLACE_ACTOR_START, actorSystem.dispatcher(), null);
    }
}


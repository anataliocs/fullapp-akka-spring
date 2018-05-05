package com.linkedin.fullappakkaspring.actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.linkedin.fullappakkaspring.actors.PriceRequestor.GetThisCryptoPrice;
import java.time.Duration;

public class Poller extends AbstractActorWithTimers {

  private static Object TICK_KEY = "TickKey";
  private final ActorRef requestorActor;
  private final String cryptoName;


  public Poller(String cryptoName, ActorRef requestorActor) {
    this.cryptoName = cryptoName;
    this.requestorActor = requestorActor;
    getTimers().startSingleTimer(TICK_KEY, new FirstTick(), Duration.ofMillis(3000));
  }

  //#greeter-messages
  static public Props props(String cryptoName, ActorRef requestorActor) {
    return Props.create(Poller.class, () -> new Poller(cryptoName, requestorActor));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(FirstTick.class, message -> {
          // do something useful here
          getTimers().startPeriodicTimer(TICK_KEY, new Tick(), Duration.ofSeconds(3));
        })
        .match(Tick.class, message -> {
          requestorActor.tell(new GetThisCryptoPrice(cryptoName), getSelf());


        })
        .build();
  }

  private static final class FirstTick {

  }

  private static final class Tick {

  }
}
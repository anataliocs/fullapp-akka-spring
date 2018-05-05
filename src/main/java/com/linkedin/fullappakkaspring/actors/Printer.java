package com.linkedin.fullappakkaspring.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.linkedin.fullappakkaspring.model.CoinBaseResponse;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

//#printer-messages
public class Printer extends AbstractActor {

  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  public Printer() {
  }
  //#printer-messages

  //#printer-messages
  static public Props props() {
    return Props.create(Printer.class, () -> new Printer());
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(CrytoPrice.class, greeting ->
            greeting.message.subscribe(coinBaseResponse ->
                log.info("[" + LocalDateTime.now() + "] "
                    + coinBaseResponse.getData().getBase()
                    + " Buy Price: $" + coinBaseResponse.getData().getAmount()
                    + " " + coinBaseResponse.getData().getCurrency()))
        )
        .build();
  }

  //#printer-messages
  static public class CrytoPrice {

    public final Mono<CoinBaseResponse> message;

    public CrytoPrice(Mono<CoinBaseResponse> message) {
      this.message = message;
    }
  }
//#printer-messages
}
//#printer-messages

package com.linkedin.fullappakkaspring.actors;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.linkedin.fullappakkaspring.actors.Printer.CrytoPrice;
import com.linkedin.fullappakkaspring.service.CoinbaseService;

//#greeter-messages
public class PriceRequestor extends AbstractActor {

  private final ActorRef printerActor;
  private CoinbaseService coinbaseService;
  //#greeter-messages

  public PriceRequestor(ActorRef printerActor, CoinbaseService coinbaseService) {
    this.printerActor = printerActor;
    this.coinbaseService = coinbaseService;
  }

  //#greeter-messages
  static public Props props(ActorRef printerActor, CoinbaseService coinbaseService) {
    return Props
        .create(PriceRequestor.class,
            () -> new PriceRequestor(printerActor, coinbaseService));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(GetThisCryptoPrice.class, wtg -> {
          printerActor
              .tell(new CrytoPrice(coinbaseService.getCryptoPrice(wtg.whatPrice)), getSelf());
        })
        .build();
  }

  //#greeter-messages
  static public class GetThisCryptoPrice {

    public final String whatPrice;

    public GetThisCryptoPrice(String what) {
      this.whatPrice = what;
    }
  }
//#greeter-messages
}
//#greeter-messages

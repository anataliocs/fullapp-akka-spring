package com.linkedin.fullappakkaspring.cmd;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.linkedin.fullappakkaspring.actors.Poller;
import com.linkedin.fullappakkaspring.actors.PriceRequestor;
import com.linkedin.fullappakkaspring.actors.Printer;
import com.linkedin.fullappakkaspring.service.CoinbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdLineUI implements CommandLineRunner {

  @Autowired
  CoinbaseService coinbaseService;

  @Override
  public void run(String... args) throws Exception {

    final ActorSystem system = ActorSystem.create("helloakka");

    System.out.println(
        "\n========================================================="
            + "\n                                                         "
            + "\n          Coinbase Price Service                         "
            + "\n          LinkedIn Learning                              "
            + "\n                                                         "
            + "\n Built by:  Chris Anatalio                               "
            + "\n=========================================================");
    System.out.println();

    final ActorRef printerActor =
        system.actorOf(Printer.props(), "printerActor");
    final ActorRef requestor =
        system.actorOf(PriceRequestor.props(printerActor, coinbaseService), "requestor");
    final ActorRef btcPoller =
        system.actorOf(Poller.props("BTC-USD", requestor), "btcPoller");
    final ActorRef ethPoller =
        system.actorOf(Poller.props("ETH-USD", requestor), "ethPoller");

  }


}
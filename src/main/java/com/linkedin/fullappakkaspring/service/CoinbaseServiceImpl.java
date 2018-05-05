package com.linkedin.fullappakkaspring.service;


import com.linkedin.fullappakkaspring.model.CoinBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CoinbaseServiceImpl implements CoinbaseService {

  @Autowired
  private WebClient webClient;

  @Override
  public Mono<CoinBaseResponse> getCryptoPrice(String priceName) {

    return webClient.get()
        .uri("https://api.coinbase.com/v2/prices/{crytoType}/buy", priceName)
        .accept(MediaType.APPLICATION_JSON)
        .exchange().flatMap(clientResponse -> clientResponse.bodyToMono(CoinBaseResponse.class));
  }
}

package com.linkedin.fullappakkaspring.service;


import com.linkedin.fullappakkaspring.model.CoinBaseResponse;
import reactor.core.publisher.Mono;

public interface CoinbaseService {

  Mono<CoinBaseResponse> getCryptoPrice(String priceName);
}

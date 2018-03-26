package com.example.webfluxdemo.services;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.model.TweetResponse;
import reactor.core.publisher.Mono;

public interface AWSDynamoService {

    Mono<Void> save(Mono<Tweet> tweet);

    Mono<TweetResponse> findById(String id);

//    Flux<Tweet> findAll();

}

package com.example.webfluxdemo.services;

import com.example.webfluxdemo.model.Tweet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AWSDynamoService {

    Mono<Void> save(Mono<Tweet> tweet);

    Mono<Tweet> findById(String id);

    Flux<List<Tweet>> findAll();

}

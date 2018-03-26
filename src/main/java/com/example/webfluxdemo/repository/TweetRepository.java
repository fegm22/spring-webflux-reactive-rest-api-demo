package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.model.Tweet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TweetRepository {

    Mono<Tweet> save(Tweet tweet);

    Mono<Tweet> findById(String id);

//    Flux<Tweet> findAll();

}

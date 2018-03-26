package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.model.Tweet;

@EnableScan
public interface TweetRepository extends ReactiveCrudRepository<Tweet, String> {

}

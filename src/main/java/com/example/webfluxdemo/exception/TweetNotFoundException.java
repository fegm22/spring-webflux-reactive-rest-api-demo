package com.example.webfluxdemo.exception;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(String tweetId) {
        super("TweetTable not found with id " + tweetId);
    }
}

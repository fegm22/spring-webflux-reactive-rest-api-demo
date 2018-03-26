package com.example.webfluxdemo.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetRequest {

    private final String id;

    @JsonCreator
    public TweetRequest(@JsonProperty("id") final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

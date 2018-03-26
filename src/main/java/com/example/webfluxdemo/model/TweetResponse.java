package com.example.webfluxdemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TweetResponse {

    private final String id;
    private final String text;
    private final String createdAt;

    @JsonCreator
    public TweetResponse(@JsonProperty("id") String id,
                         @JsonProperty("text") String text,
                         @JsonProperty("createdAt") String createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

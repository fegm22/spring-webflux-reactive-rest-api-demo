package com.example.webfluxdemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Tweet {

    @JsonIgnore
    private final String id;
    private final String text;
    @JsonIgnore
    private final String createdAt;

    @JsonCreator
    public Tweet(@JsonProperty("id") String id,
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

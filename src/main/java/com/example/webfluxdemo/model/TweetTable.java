package com.example.webfluxdemo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@DynamoDBTable(tableName = "Tweet")
public class TweetTable {

    @DynamoDBAutoGeneratedKey
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text;

    @NotNull
    private Date createdAt = new Date();

    public TweetTable(String text) {
        this.text = text;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DynamoDBAttribute(attributeName = "CreatedAt")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.webfluxdemo.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.model.TweetTable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class AWSDynamoServiceImpl implements AWSDynamoService {

    private static final String tableName = "Tweet";

    AmazonDynamoDBAsync client;
    DynamoDBMapper dynamoDBMapper;
    DynamoDB dynamoDB;

    public AWSDynamoServiceImpl(final AmazonDynamoDBAsync client) {
        this.client = client;
        this.dynamoDBMapper = new DynamoDBMapper(client);
        this.dynamoDB = new DynamoDB(client);
    }

    @Override
    public Mono<Void> save(Mono<Tweet> tweetMono) {
        return tweetMono.doOnNext(tweet -> saveTweet(tweet.getText()))
                .thenEmpty(Mono.empty());
    }

    private void saveTweet(String tweetMessage) {
        TweetTable tweetTable = new TweetTable();
        tweetTable.setText(tweetMessage);
        dynamoDBMapper.save(tweetTable);
    }

    @Override
    public Mono<Tweet> findById(String id) {
        Table table = dynamoDB.getTable(tableName);
        Item item = table.getItem("Id", id);

        Tweet tweet = new Tweet(item.get("Id").toString(),
                item.get("Text").toString(),
                item.get("CreatedAt").toString());

        return Mono.justOrEmpty(tweet);

    }

    public Flux<Tweet> findAll() {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(LocalDateTime.now().toString()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("CreatedAt < :val1").withExpressionAttributeValues(eav);

        List<TweetTable> list = dynamoDBMapper.scan(TweetTable.class, scanExpression);

        return Flux.fromIterable(list.stream()
                .map(tweetTable -> new Tweet(tweetTable.getId(), tweetTable.getText(), tweetTable.getCreatedAt().toString()))
                .collect(Collectors.toList()));

    }


}


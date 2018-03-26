package com.example.webfluxdemo.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.model.TweetTable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return tweetMono.doOnNext(tweet -> saveTweet(new TweetTable(tweet.getText())))
                .thenEmpty(Mono.empty());
    }

    private void saveTweet(TweetTable tweetTable) {
        dynamoDBMapper.save(tweetTable);
    }

    @Override
    public Mono<Tweet> findById(String id) {
        Table table = dynamoDB.getTable(tableName);
        Item item = new Item();

        try {
            item = table.getItem("Id", id);

            System.out.println("Printing item after retrieving it....");
            System.out.println(item.toJSONPretty());

        } catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

        return Mono.justOrEmpty(
                new Tweet(item.get("Id").toString(),
                        item.get("text").toString(),
                        item.get("createdAt").toString()));

    }

    @Override
    public Flux<Tweet> findAll() {
        Table table = dynamoDB.getTable(tableName);

        return Flux.empty();

    }

}


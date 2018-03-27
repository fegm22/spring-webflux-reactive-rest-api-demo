package com.example.webfluxdemo.services;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.model.TweetTable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDBAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ComparisonOperator;
import software.amazon.awssdk.services.dynamodb.model.Condition;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AWSDynamoAsyncServiceImpl implements AWSDynamoService {

    private static final String tableName = "Tweet";

    DynamoDBAsyncClient client;

    public AWSDynamoAsyncServiceImpl(final DynamoDBAsyncClient client) {
        this.client = client;
    }

    @Override
    public Mono<Void> save(Mono<Tweet> tweetMono) {
        return tweetMono.doOnNext(tweet -> saveTweet(tweet.getText()))
                .thenEmpty(Mono.empty());
    }

    private void saveTweet(String tweetMessage) {
        TweetTable tweetTable = new TweetTable();
        tweetTable.setText(tweetMessage);

        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("Text", AttributeValue.builder().s(tweetMessage).build());

        PutItemRequest putItemRequest = PutItemRequest.builder().tableName(tableName)
                .item(attributeValueHashMap)
                .build();

        CompletableFuture<PutItemResponse> completableFuture = client.putItem(putItemRequest);
    }

    @Override
    public Mono<Tweet> findById(String id) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("Id", AttributeValue.builder().s(id).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(attributeValueHashMap)
                .build();

        CompletableFuture<GetItemResponse> completableFuture = client.getItem(getItemRequest);

        CompletableFuture<Tweet> tweetCompletableFuture = completableFuture.thenApplyAsync(GetItemResponse::item)
                .thenApplyAsync(map -> new Tweet(map.get("Id").s(), map.get("Text").s(), map.get("CreatedAt").s()));

        return Mono.fromFuture(tweetCompletableFuture);

    }

    @Override
    public Flux<List<Tweet>> findAll() {
        Map<String, Condition> conditionHashMap = new HashMap<>();
        Condition condition = Condition.builder()
                .comparisonOperator(ComparisonOperator.LT)
                .attributeValueList(AttributeValue.builder().s(LocalDateTime.now().toString()).build())
                .build();

        conditionHashMap.put("CreatedAt ", condition);

        ScanRequest scanRequest = ScanRequest.builder().tableName(tableName).attributesToGet("Text")
                .scanFilter(conditionHashMap).build();

        CompletableFuture<ScanResponse> future = client.scan(scanRequest);

        CompletableFuture<List<Tweet>> response =
                future.thenApplyAsync(ScanResponse::items)
                        .thenApplyAsync(list -> list.parallelStream()
                                .map(map -> new Tweet(map.get("Id").s(), map.get("Text").s(), map.get("CreatedAt").s()
                                )).collect(Collectors.toList())
                        );


        return Flux.from(Mono.fromFuture(response));

    }

}


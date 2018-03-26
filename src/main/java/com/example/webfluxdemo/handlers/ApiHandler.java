package com.example.webfluxdemo.handlers;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.services.AWSDynamoService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class ApiHandler {

    private static final String id = "id";

    private final ErrorHandler errorHandler;

    private final AWSDynamoService awsDynamoService;

    public ApiHandler(final AWSDynamoService awsDynamoService, final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        this.awsDynamoService = awsDynamoService;
    }

    public Mono<ServerResponse> getTweet(final ServerRequest request) {
        String tweetId = request.pathVariable(id);
        Mono<Tweet> tweetResponseMono = awsDynamoService.findById(tweetId);
        return tweetResponseMono
                .flatMap(tweet -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(tweet)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(errorHandler::throwableError);
    }

    public Mono<ServerResponse> createTweet(ServerRequest request) {
        Mono<Tweet> tweetMono = request.bodyToMono(Tweet.class);
        return ServerResponse.ok().build(awsDynamoService.save(tweetMono));
    }

}
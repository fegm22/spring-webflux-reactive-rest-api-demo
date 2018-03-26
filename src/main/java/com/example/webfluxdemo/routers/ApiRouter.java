package com.example.webfluxdemo.routers;

import com.example.webfluxdemo.handlers.ApiHandler;
import com.example.webfluxdemo.handlers.ErrorHandler;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

class ApiRouter {

    private static final String TWEETS = "/tweets";
    private static final String ID = "/{id}";

    static RouterFunction<?> doRoute(final ApiHandler apiHandler, final ErrorHandler errorHandler) {
        return
                nest(path(TWEETS),
                        nest(accept(APPLICATION_JSON),
                                route(GET(ID), apiHandler::getTweet)
                                        .andRoute(POST("/"), apiHandler::createTweet)
                        ).andOther(route(RequestPredicates.all(), errorHandler::notFound))
                );
    }
}

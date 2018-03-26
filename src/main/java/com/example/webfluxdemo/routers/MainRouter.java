package com.example.webfluxdemo.routers;


import com.example.webfluxdemo.handlers.ApiHandler;
import com.example.webfluxdemo.handlers.ErrorHandler;
import org.springframework.web.reactive.function.server.RouterFunction;

public class MainRouter {

    public static RouterFunction<?> doRoute(final ApiHandler handler, final ErrorHandler errorHandler) {
        return ApiRouter
                .doRoute(handler, errorHandler)
                .andOther(StaticRouter.doRoute());
    }
}

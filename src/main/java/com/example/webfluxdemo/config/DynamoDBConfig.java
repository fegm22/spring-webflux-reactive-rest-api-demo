package com.example.webfluxdemo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsAsyncClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.example.webfluxdemo.handlers.ApiHandler;
import com.example.webfluxdemo.handlers.ErrorHandler;
import com.example.webfluxdemo.routers.MainRouter;
import com.example.webfluxdemo.services.AWSDynamoService;
import com.example.webfluxdemo.services.AWSDynamoServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
@EnableWebFlux
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String dBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

    private AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    private AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public AmazonDynamoDBAsync amazonDynamoDB() {
        AwsAsyncClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsAsyncClientBuilder.EndpointConfiguration(dBEndpoint, Regions.EU_WEST_1.getName());

        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(amazonAWSCredentialsProvider())
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    @Bean
    ApiHandler apiHandler(final AWSDynamoService awsDynamoService, final ErrorHandler errorHandler) {
        return new ApiHandler(awsDynamoService, errorHandler);
    }

    @Bean
    AWSDynamoService awsDynamoService(final AmazonDynamoDBAsync client) {
        return new AWSDynamoServiceImpl(client);
    }

    @Bean
    ErrorHandler errorHandler() {
        return new ErrorHandler();
    }

    @Bean
    RouterFunction<?> mainRouterFunction(final ApiHandler apiHandler, final ErrorHandler errorHandler) {
        return MainRouter.doRoute(apiHandler, errorHandler);
    }
}

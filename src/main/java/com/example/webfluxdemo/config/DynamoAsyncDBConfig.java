package com.example.webfluxdemo.config;

import com.example.webfluxdemo.handlers.ApiHandler;
import com.example.webfluxdemo.handlers.ErrorHandler;
import com.example.webfluxdemo.routers.MainRouter;
import com.example.webfluxdemo.services.AWSDynamoAsyncServiceImpl;
import com.example.webfluxdemo.services.AWSDynamoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import software.amazon.awssdk.auth.AwsCredentials;
import software.amazon.awssdk.auth.AwsCredentialsProvider;
import software.amazon.awssdk.auth.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDBAsyncClient;

import java.net.URI;


@Configuration
@EnableWebFlux
public class DynamoAsyncDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String dBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

    private AwsCredentialsProvider amazonAWSCredentialsProvider() {
        return new StaticCredentialsProvider(amazonAWSCredentials());
    }

    private AwsCredentials amazonAWSCredentials() {
        return new AwsCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public DynamoDBAsyncClient dynamoDBAsyncClient()  {

        return DynamoDBAsyncClient.builder()
                .region(Region.EU_WEST_1)
                .endpointOverride(URI.create(dBEndpoint))
                .credentialsProvider(amazonAWSCredentialsProvider())
                .build();
    }

    @Bean
    ApiHandler apiHandler(final AWSDynamoService awsDynamoService, final ErrorHandler errorHandler) {
        return new ApiHandler(awsDynamoService, errorHandler);
    }

    @Bean
    AWSDynamoService awsDynamoService(final DynamoDBAsyncClient dynamoDBAsyncClient) {
        return new AWSDynamoAsyncServiceImpl(dynamoDBAsyncClient);
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

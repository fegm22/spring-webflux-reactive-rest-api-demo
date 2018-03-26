package com.example.webfluxdemo;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxDemoApplicationTests {

//	@Autowired
//	private WebTestClient webTestClient;
//
//	@Autowired
//    AWSDynamoService AWSDynamoService;
//
//	@Test
//	public void testCreateTweet() {
//		TweetTable tweet = new TweetTable("This is a Test TweetTable");
//
//		webTestClient.post().uri("/tweets")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(tweet), TweetTable.class)
//				.exchange()
//				.expectStatus().isOk()
//				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//				.expectBody()
//                .jsonPath("$.id").isNotEmpty()
//                .jsonPath("$.text").isEqualTo("This is a Test TweetTable");
//	}
//
//	@Test
//    public void testGetAllTweets() {
//	    webTestClient.get().uri("/tweets")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBodyList(TweetTable.class);
//    }
//
//    @Test
//    public void testGetSingleTweet() {
//        TweetTable tweet = AWSDynamoService.save(new TweetTable("Hello, World!")).block();
//
//        webTestClient.get()
//                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .consumeWith(response ->
//                        Assertions.assertThat(response.getResponseBody()).isNotNull());
//    }
//
//    @Test
//    public void testUpdateTweet() {
//        TweetTable tweet = AWSDynamoService.save(new TweetTable("Initial TweetTable")).block();
//
//        TweetTable newTweetData = new TweetTable("Updated TweetTable");
//
//        webTestClient.put()
//                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(newTweetData), TweetTable.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody()
//                .jsonPath("$.text").isEqualTo("Updated TweetTable");
//    }
//
//    @Test
//    public void testDeleteTweet() {
//	    TweetTable tweet = AWSDynamoService.save(new TweetTable("To be deleted")).block();
//
//	    webTestClient.delete()
//                .uri("/tweets/{id}", Collections.singletonMap("id",  tweet.getId()))
//                .exchange()
//                .expectStatus().isOk();
//    }
}

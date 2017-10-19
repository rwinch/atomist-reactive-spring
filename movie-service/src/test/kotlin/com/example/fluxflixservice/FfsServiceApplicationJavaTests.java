package com.example.fluxflixservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FfsServiceApplicationJavaTests {
	@Autowired
	ApplicationContext context;

	WebTestClient client;

	@Before
	public void setup() {
		this.client = WebTestClient
				.bindToApplicationContext(this.context)
                .apply(springSecurity())
                .configureClient()
				.filter(basicAuthentication())
				.baseUrl("http://localhost:8080/")
				.build();
	}

	@Test
	public void getMoviesWhenNotAuthenticatedThenIsUnauthorized() {
		this.client.get()
				.uri("/movies/")
				.exchange()
				.expectStatus().isUnauthorized();
	}
}

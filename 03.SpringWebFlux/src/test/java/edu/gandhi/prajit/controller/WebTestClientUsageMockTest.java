package edu.gandhi.prajit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import edu.gandhi.prajit.repository.ProductRepostory;
import edu.gandhi.prajit.resources.ProductController;
import edu.gandhi.prajit.resources.api.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class WebTestClientUsageMockTest {
	private WebTestClient webTestClient;
	private List<Product> expectedProducts;
	
	@MockBean
	private ProductRepostory productRepostory;

	@BeforeEach
	void beforeEach() {
		this.webTestClient = WebTestClient.bindToController(new ProductController(productRepostory)).configureClient()
				.baseUrl("/api/v1/products").build();
		this.expectedProducts = Arrays.asList(Product.builder().id("74539f889a09a98bf7949dfbcd4c9ef6d5369ac44c570a9eba4d22f0dd9213aa").name("Reynolds").price(5.99).build());
	}

	@Test
	void getAllProducts() {
		when(this.productRepostory.findAll()).thenReturn(Flux.fromIterable(this.expectedProducts));
		this.webTestClient.get().uri("/").exchange()//Always Use Exchange
			.expectStatus().isOk()
			.expectBodyList(Product.class).isEqualTo(this.expectedProducts);
	}
	@Test
	void productInvalidIdNotFound() {
		when(this.productRepostory.findById("not-listed-product")).thenReturn(Mono.<Product>empty());
		this.webTestClient.get().uri("/not-listed-product").exchange()//Always Use Exchange
			.expectStatus().isNotFound();
	}
	@Test
    void productIdFound() {
		Product expectedProduct = this.expectedProducts.get(0);
        when(this.productRepostory.findById(expectedProduct.getId())).thenReturn(Mono.just(expectedProduct));
        this.webTestClient.get().uri("/{id}", expectedProduct.getId()).exchange()//Always Use Exchange
	        .expectStatus().isOk()
	        .expectBody(Product.class).isEqualTo(expectedProduct);
    }
	@Test
    void serverSideEvent() {
		 FluxExchangeResult<String> resultFlux = 
			this.webTestClient.get().uri("/server-side-event").accept(MediaType.TEXT_EVENT_STREAM).exchange()
		 		.expectStatus().isOk()
		 		.returnResult(String.class);
		 StepVerifier.create(resultFlux.getResponseBody())
		 	.expectNextCount(1)
		 	.consumeNextWith(event->assertThat(event).contains("EventId:1 @Time:-"))
		 	.thenCancel().verify();
	}
}

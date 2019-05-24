package edu.gandhi.prajit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import edu.gandhi.prajit.repository.ProductRepostory;
import edu.gandhi.prajit.resources.api.Product;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WebTestClientUsageApplicationContextTest {
	private WebTestClient webTestClient;
	private List<Product> expectedProducts;
	
	@Autowired
	private ProductRepostory productRepostory;
	@Autowired
	private ApplicationContext applicationContext;
	
	@BeforeEach
	public void initialization() {
		this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).configureClient().baseUrl("/api/v1/products").build();
		this.expectedProducts = productRepostory.findAll().collectList().block();		
	}
	
	@Test
	void getAllProducts() {
		this.webTestClient.get().uri("/").exchange()//Always Use Exchange
			.expectStatus().isOk()
			.expectBodyList(Product.class).isEqualTo(this.expectedProducts);
	}
	@Test
	void productInvalidIdNotFound() {
		this.webTestClient.get().uri("/not-listed-product").exchange()//Always Use Exchange
			.expectStatus().isNotFound();
	}
	@Test
    void productIdFound() {
        Product expectedProduct = this.expectedProducts.get(0);
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

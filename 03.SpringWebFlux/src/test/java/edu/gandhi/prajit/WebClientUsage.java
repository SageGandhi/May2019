package edu.gandhi.prajit;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import edu.gandhi.prajit.resources.api.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientUsage {
//		WebClient.create();
//		WebClient.create("http://localhost:8080/api/v2/products");
//		WebClient.builder().baseUrl("http://localhost:8080/api/v2/products").defaultHeader("Author-User","Prajit").build();
//		WebClient.create().mutate().baseUrl("http://localhost:8080/api/v2/products").build();
	
//		BodyInserters.fromMultipartData/BodyInserters.fromFormData
//		For File Use org.springframework.http.client.MultipartBodyBuilder/org.springframework.core.io.FileSystemResource
		
//		BodyInserters.fromFormData("Name","Prajit").with("LastName","Gandhi");
//		BodyInserters.fromMultipartData("FileName","Upload.jpg").with("LastName","Gandhi");
		
//		MultiValueMap<String,String>==>new LinkedMultiValueMap<>();
		
//		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
//		multipartBodyBuilder.part("Json", Product.builder().name("Reynolds").price(5.99).build());
//		multipartBodyBuilder.part("Content",new FileSystemResource("."));
//		multipartBodyBuilder.build();//MultiValueMap<String, HttpEntity<?>>
	final WebClient webClient = WebClient.builder()
			.baseUrl("http://localhost:8080/api/v1/products")
			.build();
	// @formatter:off
	public Mono<ResponseEntity<Product>> createProductUsingWebClient() {
		return webClient
			.post()// WebClient.RequestBodyUriSpec
			.body(Mono.just(Product// WebClient.RequestHeadersSpec
					.builder()
					.name("Reynolds")
					.price(5.99)
					.build()), Product.class)
			.exchange()// Mono<ClientResponse> 
			.flatMap(response->response.toEntity(Product.class))// Mono<ResponseEntity<Product>> 
			.log()
			.doOnSuccess(msg->System.err.println("Post:Create:"+msg));
	}
	public Flux<Product> getAllProductsUsingWebClient() {
		return webClient
			.get()
			.retrieve()
			.bodyToFlux(Product.class)
			.doOnNext(msg->System.err.println("Get:Retrieve:"+msg));
	}
	public Mono<Product> updateProductUsingWebClient(String id, String name, double price) {
        return webClient
            .put()
            .uri("/{id}", id)
            .body(Mono.just(Product
            		.builder()
            		.name(name)
            		.price(price)
            		.build()), Product.class)
            .retrieve()
            .bodyToMono(Product.class)
            .doOnSuccess(msg->System.err.println("Put:Update:"+msg));
    }
	public Mono<Void> deleteProductUsingWebClient(String id) {
        return webClient
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(msg->System.err.println("Delete:"+msg));
    }
	public Flux<String> getAllEventsUsingWebClient() {
        return webClient
            .get()
            .uri("/server-side-event")
            .retrieve()
            .bodyToFlux(String.class);
    }
	// @formatter:on
	@Test
	public void testAllApiUsingWebClient() throws Exception {
		this.createProductUsingWebClient()
			.thenMany(this.getAllProductsUsingWebClient())
			.take(1)
			.flatMap(product->this.updateProductUsingWebClient(product.getId(), product.getName().toUpperCase(),product.getPrice()))
			.flatMap(product->this.deleteProductUsingWebClient(product.getId()))
			.thenMany(this.getAllProductsUsingWebClient())
			.thenMany(this.getAllEventsUsingWebClient())
			.subscribe(System.err::println);
		Thread.sleep(15000);
	}
}

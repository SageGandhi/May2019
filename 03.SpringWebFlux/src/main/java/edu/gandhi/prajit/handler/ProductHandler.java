package edu.gandhi.prajit.handler;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import edu.gandhi.prajit.repository.ProductRepostory;
import edu.gandhi.prajit.resources.api.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {
	@Autowired
	private ProductRepostory productRepostory;
	
	public Mono<ServerResponse> findAllProducts(ServerRequest request) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productRepostory.findAll(),Product.class);
	}
	public Mono<ServerResponse> findProductById(ServerRequest request) {
		return productRepostory.findById(request.pathVariable("id"))
				.flatMap(product->ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromObject(product)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	public Mono<ServerResponse> saveProduct(ServerRequest request) {
		Mono<Product> product = request.bodyToMono(Product.class);
		return product.flatMap(entity->
			ServerResponse
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(productRepostory.save(entity),Product.class));
	}
	public Mono<ServerResponse> updateProduct(ServerRequest request) {
		final Mono<Product> requestProduct = request.bodyToMono(Product.class);
		final Mono<Product> entityProduct = productRepostory.findById(request.pathVariable("id"));
		return requestProduct.zipWith(entityProduct,(productInRequest,productInDb)->
			Product.builder()
				.id(productInDb.getId())
				.name(productInRequest.getName())
				.price(productInRequest.getPrice())
				.build())
			.flatMap(product->ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(productRepostory.save(product),Product.class))
			.switchIfEmpty(ServerResponse.notFound().build());
	}
	public Mono<ServerResponse> deleteProductById(ServerRequest request) {
		final Mono<Product> entityProduct = productRepostory.findById(request.pathVariable("id"));
		return entityProduct
			.flatMap(entity -> ServerResponse.ok().build(productRepostory.delete(entity)))
			.switchIfEmpty(ServerResponse.notFound().build());
	}
	public Mono<ServerResponse> deleteAllProduct(ServerRequest request) {
		return ServerResponse.ok().build(productRepostory.deleteAll());
	}
	public Mono<ServerResponse> getProductEvents(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
			.body(Flux.interval(Duration.ofSeconds(1))
					.map(index->MessageFormat.format("EventId:{0} @Time:-{1} For Product.",
						index,LocalDateTime.now())),String.class);
	}
}

package edu.gandhi.prajit.resources;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.gandhi.prajit.repository.ProductRepostory;
import edu.gandhi.prajit.resources.api.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	private ProductRepostory productRepostory;

	public ProductController(ProductRepostory productRepostory) {
		this.productRepostory = productRepostory;
	}

	@GetMapping
	public Flux<Product> findAllProducts() {
		return productRepostory.findAll();
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<Product>> findProductById(@PathVariable("id") String id) {
		return productRepostory.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> saveProduct(@RequestBody Product product) {
		return productRepostory.save(product);
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<Product>> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
		return productRepostory.findById(id).flatMap(dbProduct -> {
			dbProduct.setName(product.getName());
			dbProduct.setPrice(product.getPrice());
			return productRepostory.save(dbProduct);
		}).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> deleteProductById(@PathVariable("id") String id) {
		return productRepostory.findById(id)
				.flatMap(entity -> productRepostory.delete(entity).then(Mono.just(ResponseEntity.ok().<Void>build())))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping
	public Mono<Void> deleteAllProduct() {
		return productRepostory.deleteAll();
	}
	@GetMapping(value = "/server-side-event",produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<String> getProductEvents(){
		return Flux.interval(Duration.ofSeconds(1))
			.map(index->MessageFormat.format("EventId:{0} @Time:-{1} For Product.",index,LocalDateTime.now()));
	}
}

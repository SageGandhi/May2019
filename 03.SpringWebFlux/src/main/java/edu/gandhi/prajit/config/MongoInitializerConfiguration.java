package edu.gandhi.prajit.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import edu.gandhi.prajit.repository.ProductRepostory;
import edu.gandhi.prajit.resources.api.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class MongoInitializerConfiguration {
	private boolean usingEmbededMongo = true;

	@Bean
	public CommandLineRunner initialize(ReactiveMongoOperations reactiveMongoOperations, ProductRepostory repository) {
		return args -> {
			// In Memory No Need To Check Existing Collection In Mongo[Embeded]
			if (usingEmbededMongo) {
				Flux.just(Product.builder().name("Apple 2018 13-Inch MacBook Pros").price(1529.99).build(),
						Product.builder().name("OnePlus 7 Pro (Mirror Grey, 6GB RAM, 128GB Storage)").price(702.38).build(),
						Product.builder().name("OnePlus 6T (Midnight Black, 8GB RAM, 128GB Storage)").price(473.02).build())
					.flatMap(repository::save).thenMany(repository.findAll()).log().subscribe(System.out::println);
			} else {
				// Actual Mongo Database Exists
				reactiveMongoOperations.collectionExists(Product.class)
						.flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Product.class)
								: Mono.just(exists))
						.thenMany(value -> reactiveMongoOperations.createCollection(Product.class))
						.thenMany(Flux.just(
								Product.builder().name("Apple 2018 13-Inch MacBook Pros").price(1529.99).build(),
								Product.builder().name("OnePlus 7 Pro (Mirror Grey, 6GB RAM, 128GB Storage)").price(702.38).build(),
								Product.builder().name("OnePlus 6T (Midnight Black, 8GB RAM, 128GB Storage)").price(473.02).build())
							.flatMap(repository::save))
						.thenMany(repository.findAll()).log().subscribe(System.out::println);
			}
		};
	}
}

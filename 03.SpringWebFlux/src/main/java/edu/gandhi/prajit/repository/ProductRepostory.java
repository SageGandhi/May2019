package edu.gandhi.prajit.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import edu.gandhi.prajit.resources.api.Product;

public interface ProductRepostory extends ReactiveMongoRepository<Product, String>{

}

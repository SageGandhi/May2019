package edu.gandhi.prajit.resources.api;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Product {
	private String id;
	private String name;
	private Double price;
}

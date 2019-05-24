package edu.gandhi.prajit.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import edu.gandhi.prajit.handler.ProductHandler;

@Configuration
public class RouterConfiguration {
	@Bean
	public RouterFunction<ServerResponse> routesSimplified(ProductHandler productHandler) {
		// @formatter:off
		//Url Order Is Important
		return route(GET("/api/v2/products").and(accept(APPLICATION_JSON)),productHandler::findAllProducts)
				.andRoute(POST("/api/v2/products").and(contentType(APPLICATION_JSON)),productHandler::saveProduct)
				.andRoute(DELETE("/api/v2/products").and(accept(APPLICATION_JSON)),productHandler::deleteAllProduct)
				.andRoute(GET("/api/v2/products/server-side-event").and(accept(TEXT_EVENT_STREAM)),productHandler::getProductEvents)
				.andRoute(GET("/api/v2/products/{id}").and(accept(APPLICATION_JSON)),productHandler::findProductById)
				.andRoute(PUT("/api/v2/products/{id}").and(contentType(APPLICATION_JSON)),productHandler::updateProduct)			
				.andRoute(DELETE("/api/v2/products/{id}").and(accept(APPLICATION_JSON)),productHandler::deleteProductById);
		// @formatter:on
	}

	@Bean
	public RouterFunction<ServerResponse> routesNested(ProductHandler productHandler) {
		// @formatter:off
		return nest(path("/api/v3/products"),
				route(GET("/").and(accept(APPLICATION_JSON)),productHandler::findAllProducts)
				.andRoute(method(HttpMethod.POST).and(contentType(APPLICATION_JSON)),productHandler::saveProduct)
				.andRoute(DELETE("/").and(accept(APPLICATION_JSON)),productHandler::deleteAllProduct)
				.andRoute(GET("/server-side-event").and(accept(TEXT_EVENT_STREAM)),productHandler::getProductEvents)
				.andNest(path("/{id}"),
						route(GET("/").and(accept(APPLICATION_JSON)),productHandler::findProductById)
						.andRoute(PUT("/").and(contentType(APPLICATION_JSON)),productHandler::updateProduct)			
						.andRoute(DELETE("/").and(accept(APPLICATION_JSON)),productHandler::deleteProductById)));
		// @formatter:on
	}
}

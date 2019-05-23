package edu.gandhi.prajit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUsage {
	@Test
	public void createWebClient() {
		assertThat(WebClient.create()).isNotNull();
		assertThat(WebClient.create("http://localhost:8080/api/v2/products")).isNotNull();
		assertThat(WebClient.builder().baseUrl("http://localhost:8080/api/v2/products").defaultHeader("Author-User","Prajit").build()).isNotNull();
		assertThat(WebClient.create().mutate().baseUrl("http://localhost:8080/api/v2/products").build()).isNotNull();
	}
	@Test
	public void getRequestWebClient() {
//		BodyInserters.fromMultipartData/BodyInserters.fromFormData
//		For File Use org.springframework.http.client.MultipartBodyBuilder/org.springframework.core.io.FileSystemResource
		final WebClient webClient = WebClient.create("http://localhost:8080/api/v2/products");
		webClient.get().accept(MediaType.APPLICATION_JSON).exchange().subscribe(System.out::println);
	}
}

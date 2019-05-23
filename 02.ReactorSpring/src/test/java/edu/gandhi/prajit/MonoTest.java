package edu.gandhi.prajit;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class MonoTest {
	@Test
	void monoExamples() {
		Mono.just("Prajit Gandhi:01").log().subscribe();
		Mono.just("Prajit Gandhi:02").log().subscribe(System.out::println);
		Mono.just("Prajit Gandhi:03").log().doOnSubscribe(System.out::println).doOnRequest(System.out::println)
				.doOnSuccess(System.out::println)
				.subscribe(System.out::println, System.err::println, System.out::println);
		Mono.error(new RuntimeException("MonoRuntimeException:01")).doOnError(System.err::println)
				.onErrorResume(throwable -> Mono.just("NewMono:01")).log().subscribe();
		Mono.error(new RuntimeException("MonoRuntimeException:02")).doOnError(System.err::println)
				.onErrorReturn("NewMono:02").log().subscribe();
	}
	@Test
	void fluxExamples() {
		Flux.just("247119","262626","290580").log().subscribe();
		Flux.just(Arrays.asList("247119","262626","290580")).log().subscribe();
	}
}

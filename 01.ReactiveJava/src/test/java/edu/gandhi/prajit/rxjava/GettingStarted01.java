package edu.gandhi.prajit.rxjava;

import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

class GettingStarted01 {
	@Test void observableCreateFactory() {
		final Observable<String> source = Observable.create(emitter -> {
			try {
				emitter.onNext("01-Create");
				emitter.onNext("02-Create");
				emitter.onComplete();
			} catch (Exception exception) {
				emitter.onError(exception);
			}
		});
		source.subscribe(System.out::println);
	}
	@Test void observableJustFactory() {
		final Observable<String> source = Observable.just("01-Just","02-Just");
		source.subscribe(System.out::println);
	}
	@Test void observableObserverSubscribe() {
		final Observable<String> source = Observable.just("01-Observable","02-Observable");
		source.subscribe(new Observer<String>() {
			@Override
			public void onSubscribe(Disposable disposable) {
				System.out.println(disposable);
			}
			@Override
			public void onNext(String next) {
				System.out.println("Value:"+next);
			}
			@Override
			public void onError(Throwable throwable) {
				System.out.println("Throwable:"+throwable);
				throwable.printStackTrace();
			}
			@Override
			public void onComplete() {
				System.out.println("Completed.");
			}
		});		
	}
}
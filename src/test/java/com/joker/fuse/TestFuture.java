package com.joker.fuse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestFuture<R> {
	public Future<R> test() {
		Future<R> f = new Future<R>() {

			public boolean cancel(boolean mayInterruptIfRunning) {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isCancelled() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isDone() {
				// TODO Auto-generated method stub
				return false;
			}

			public R get() throws InterruptedException, ExecutionException {
				return null;
			}

			public R get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		return f;
	}
}

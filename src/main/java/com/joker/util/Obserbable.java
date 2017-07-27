package com.joker.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;



public class Obserbable<R> {
	private Func<R> func;
	private Action terminateAction;
	private Action unsubscribeAction;
	private Action completedAction;
	
	//成功的完成所有成功请求
	public Obserbable<R> doOnTerminate(Action terminateAction) {
		this.terminateAction = terminateAction;
		return this;
	}
	
	//
	public Obserbable<R> doOnUnsubscribe(Action unsubscribeAction) {
		this.unsubscribeAction = unsubscribeAction;
		return this;
	}
	
	//成功完成所有（包括失败，拒绝）
	public Obserbable<R> doOnCompleted (Action completedAction) {
		this.unsubscribeAction = completedAction;
		return this;
	}
	
	public Future<R> toFuture() {
		Obserbable<R> thisObserver = this;
		final Func<R> thisFunc = thisObserver.func;
		return new Future<R>() {
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}

			public boolean isCancelled() {
				return false;
			}

			public boolean isDone() {
				return false;
			}

			public R get() throws InterruptedException, ExecutionException {
				return thisFunc.call();
			}

			public R get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				return null;
			}
		};
	}

	public void setFunc(Func<R> func) {
		this.func = func;
	}

	
	
	
	
}

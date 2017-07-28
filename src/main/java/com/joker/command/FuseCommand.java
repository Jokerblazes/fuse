package com.joker.command;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.joker.entity.CommandKey;
import com.joker.rx.observer.Subscriber;


public abstract class FuseCommand<R> extends AbstractCommand<R> {
	
	
	protected FuseCommand(String key,Class classes) {
		super(key,classes);
	}

	public R execute() throws InterruptedException, ExecutionException {
		return queue().get();
	}
	
	public Future<R> queue() {
		Future<R> future = toObservable().toFuture();
		return future;
	}

	public R fallBack() {
		return null;
	}
	
	abstract protected R run(); 
	
	@Override
	public R doRun() {
		return run();
	}
	
	
	
}

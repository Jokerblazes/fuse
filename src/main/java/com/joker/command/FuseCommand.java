package com.joker.command;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;



public abstract class FuseCommand<R> extends AbstractCommand<R> {
	
	private final static long OUTTIME = 2000;
	
	protected FuseCommand(String key,Class classes) {
		super(key,classes);
	}

	public R execute() {
		try {
			return get();
		} catch (Exception e) {
			return fallBack();
		}
	}
	
	public Future<R> queue() {
		Future<R> future = toObservable().toFuture();
		return future;
	}
	
	private R get() throws InterruptedException, ExecutionException, TimeoutException {
		return queue().get(OUTTIME,TimeUnit.MILLISECONDS);
	}

	public abstract R fallBack();
	
	abstract protected R run(); 
	
	@Override
	public R doRun() {
		commandState.set(CommandState.USER_CODE_EXECUTED);
		//1:判断熔断规则
		//2:若熔断器打开，直接执行fallback
		//3:若run超时，直接执行fallback
		if (commandRule.isPermission()) {
			return fallBack();
		} 
		return run();
	}
	
	
	
}

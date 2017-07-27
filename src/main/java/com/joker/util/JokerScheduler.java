package com.joker.util;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.joker.rx.Scheduler;
import com.joker.rx.Subscription;
import com.joker.rx.functions.Action0;

public class JokerScheduler extends Scheduler {
	private final ThreadPoolExecutor threadPool;
	
	public JokerScheduler(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}
	
	@Override
	public Worker createWorker() {
		return new JokerWorker();
	}
	
	private  class JokerWorker extends Worker {

		@Override
		public Subscription schedule(Action0 arg0) {
			arg0.call();
			return null;
		}

		@Override
		public Subscription schedule(Action0 arg0, long arg1, TimeUnit arg2) {
			return null;
		}
		
	}

}

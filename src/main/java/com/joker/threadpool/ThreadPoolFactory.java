package com.joker.threadpool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.joker.entity.CommandKey;

public class ThreadPoolFactory {
	private ThreadPoolFactory() {}
	private static Map<String,ThreadPoolExecutor> map = new ConcurrentHashMap<String, ThreadPoolExecutor>();
	
	static {
		initPool();
	}
	
//	ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.DAYS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static void initPool() {
		//从xml读取key
		String[] keys = new String[2];
		for (String key : keys) {
			map.put(key, new ThreadPoolExecutor(5, 5, 5, TimeUnit.DAYS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy()));
		}
	}
	
	public static ThreadPoolExecutor getPoolInstance(CommandKey key) {
		return map.get(key.getKey());
	}
	
	
	
	
	
}

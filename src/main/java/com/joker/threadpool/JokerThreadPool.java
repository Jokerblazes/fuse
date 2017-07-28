package com.joker.threadpool;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.joker.entity.CommandKey;
import com.joker.rx.Scheduler;
import com.joker.util.JokerScheduler;

public interface JokerThreadPool  {
	public ExecutorService geExecutorService();
	
	public Scheduler getScheduler();
	
	public static class Factory {
		final static Map<String,JokerThreadPool> threadPools = new ConcurrentHashMap<String, JokerThreadPool>();
		
		public static JokerThreadPool getInstance(CommandKey key) {
			String key_name = key.getKey();
			JokerThreadPool pool = threadPools.get(key_name);
			if (pool != null)
				return pool;
			synchronized (JokerThreadPool.class) {
				if (! threadPools.containsKey(key_name)) {
					threadPools.put(key_name, new JokerThreadPoolDefault());
				}
			}
			return threadPools.get(key_name);
		}
		
		static synchronized void shutdown() {
			for (JokerThreadPool pool : threadPools.values()) {
				pool.geExecutorService().shutdown();
			}
			threadPools.clear();
		}
		
		static synchronized void shutdown(long timeout,TimeUnit unit) {
			for (JokerThreadPool pool : threadPools.values()) {
				pool.geExecutorService().shutdown();
			}
			for (JokerThreadPool pool : threadPools.values()) {
				try {
					while (pool.geExecutorService().awaitTermination(timeout, unit)) {
					}
				} catch (InterruptedException e) {
					throw new RuntimeException("关闭线程池超时！");
				}
			}
			threadPools.clear();
		}
		
	}
	
	public static class JokerThreadPoolDefault implements JokerThreadPool {
		private final ThreadPoolExecutor threadPool;
		private final BlockingQueue<Runnable> queue;
		
		
		public JokerThreadPoolDefault() {
			//TODO 线程池初始化
			this.threadPool = new ThreadPoolExecutor(5, 5, 5, TimeUnit.DAYS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
			this.queue = threadPool.getQueue();
		}

		public ExecutorService geExecutorService() {
			return threadPool;
		}

		public Scheduler getScheduler() {
			return new JokerScheduler(threadPool);
		}
		
	}
	
}

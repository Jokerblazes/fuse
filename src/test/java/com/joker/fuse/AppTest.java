package com.joker.fuse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		testFuture();
	}
	
	private static void testFuture() {
		TestFuture<String> tf = new TestFuture<String>();
		Future f = tf.test();
		try {
			f.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

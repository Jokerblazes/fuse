package com.joker.command;

import com.joker.command.rule.DefaultCommandRule;
import com.joker.entity.User;

public class GetUserCommand extends FuseCommand<User> {
	
	public GetUserCommand (String key,Class classes) {
		super(key,classes);
	}
	
	public GetUserCommand (Class classes) {
		this("user",classes);
	}
	
	public GetUserCommand (String key) {
		this(key,DefaultCommandRule.class);
	}
	
	public GetUserCommand() {
		this("user");
	}
	
	
	
	@Override
	protected User run() {
//		try {
//			Thread.sleep((int) (Math.random() * 10) + 2);
//		} catch (InterruptedException e) {
//			// do nothing
//		}
//
//		/* fail 5% of the time to show how fallback works */
//		if (Math.random() > 0.95) {
//			throw new RuntimeException("random failure processing UserAccount network response");
//		}
//
//		/* latency spike 5% of the time so timeouts can be triggered occasionally */
//		if (Math.random() > 0.95) {
//			// random latency spike
//			try {
//				Thread.sleep((int) (Math.random() * 300) + 25);
//			} catch (InterruptedException e) {
//				// do nothing
//			}
//		}
//
//		/* success ... create UserAccount with data "from" the remote service response */
		return new User(1, "Joker");
	}
	
	@Override
	public User fallBack() {
		return new User(0,"default");
	}
	
}

package com.joker.command.rule;

public interface CommandRule {
	
	public boolean failExecute();
	
	public boolean successExecute();
	
	public boolean isPermission();
	
}

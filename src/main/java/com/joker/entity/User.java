package com.joker.entity;



public class User {
	private int user_id;
	private String user_account;
	
	
	public User(int user_id, String user_account) {
		super();
		this.user_id = user_id;
		this.user_account = user_account;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_account=" + user_account + "]";
	}

	
	
	
	
	
	
	
	

}

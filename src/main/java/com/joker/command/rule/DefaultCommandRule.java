package com.joker.command.rule;


public class DefaultCommandRule implements CommandRule {
	private double FAULT_RATE;
	private int total_number;
	private int fault_number;
	private double PERMISSION_RATE = 0.8;
	public DefaultCommandRule (double PERMISSION_RATE) {
		this.PERMISSION_RATE = PERMISSION_RATE;
	}
	
	public DefaultCommandRule() {}
	

	public boolean failExecute() {
		fault_number ++;
		total_number ++;
		update();
		return isPermission();
	}

	public boolean successExecute() {
		total_number ++;
		update();
		return isPermission();
	}
	
	private void update() {
		this.FAULT_RATE = this.fault_number / this.total_number;
	}
	
	public boolean isPermission() {
		return this.PERMISSION_RATE < this.FAULT_RATE;
	}
	
	
	

}

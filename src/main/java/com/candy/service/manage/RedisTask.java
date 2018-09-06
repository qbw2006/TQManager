package com.candy.service.manage;

public class RedisTask {
	
	//0:stop 1:start
	private int opertion;
	
	private String id;

	public int getOpertion() {
		return opertion;
	}

	public void setOpertion(int opertion) {
		this.opertion = opertion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RedisTask [opertion=" + opertion + ", id=" + id + "]";
	}	
}

package com.candy.dao;

import com.candy.service.OperationTask;

public interface IOperationTask {

	void handleTask(String message);
	
	void addTask(OperationTask ot);
}

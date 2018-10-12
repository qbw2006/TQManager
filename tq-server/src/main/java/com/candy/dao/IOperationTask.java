package com.candy.dao;

import com.candy.service.OperationTask;

public interface IOperationTask {

    /**
     * 处理redis启动停止任务
     * 
     * @param message
     */
    void handleTask(String message);

    /**
     * 添加redis启动停止任务
     * 
     * @param ot
     */
    void addTask(OperationTask ot);
}

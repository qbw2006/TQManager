package com.candy.dao;

import java.util.List;

import com.candy.service.OperationTask;

public interface IOperationDao {
	
	/**
	 * 查询所有redis服务器
	 * @return
	 */
	List<RedisServerEntity> findAllServer();
	
	/**
	 * 根据id查询redis-server-entity
	 * @param id
	 * @return
	 */
	RedisServerEntity findServerById(String id);
		
	/**
	 * 修改RedisServerEntity
	 * @param rse
	 */
	void modifyServer(RedisServerEntity rse);
	
	/**
	 * 删除redis entity
	 * @param id
	 */
	void deleteServer(String id);
	
	/**
	 * 添加redis服务器
	 * @param rse
	 * @return
	 */
	String addServer(RedisServerEntity rse);
	
	/**
	 * 添加检查结果
	 * @param rr
	 */
	void addResult(RedisResult rr);
	
	/**
	 * 查询检查结果
	 * @return
	 */
	List<RedisResult> findAllResult();
	
	/**
	 * 删除检查结果
	 */
	void deleteResult(String id);
	
	/**
	 * 添加启动或者停止任务
	 * @param ot
	 */
	void addTask(OperationTask ot);
		
}

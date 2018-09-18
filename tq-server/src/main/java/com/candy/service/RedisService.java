package com.candy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candy.dao.IOperationDao;
import com.candy.dao.RedisResult;
import com.candy.dao.RedisServerEntity;

@Service
public class RedisService {
	
	@Autowired
	private IOperationDao rDao; 
	
	public List<RedisResult> healthInfo()
	{
		return rDao.findAllResult();
	}
	
	public String addServer(RedisServerEntity rse)
	{
		return rDao.addServer(rse);
	}
	public void modifyServer(RedisServerEntity rse)
	{
		 rDao.modifyServer(rse);
	}
	
	public void deleteServer(String id)
	{
		 rDao.deleteServer(id);
	}
	
	public void pubRedisTask(OperationTask ot)
	{
		rDao.addTask(ot);
	}
	
}

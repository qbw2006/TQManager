package com.candy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.dao.RedisResult;
import com.candy.dao.RedisServerEntity;
import com.candy.service.OperationTask;
import com.candy.service.RedisService;
import com.candy.utils.JSONMessage;
import com.candy.utils.TqLog;

@RestController
@RequestMapping("/tqmanager/redis")
public class RedisController {

	@Autowired
	private RedisService rService;
	
	@RequestMapping(value= "/health", method = RequestMethod.GET)
	public String healthInfo()
	{
		List<RedisResult> res = rService.healthInfo();
		
		JSONObject json = new JSONObject();
		json.put("health", res);
		
		return JSONMessage.createSuccess().addData(json).toString();
	}
	
	@RequestMapping(value= "/task", method = RequestMethod.POST)
	public String pubRedisTask(@Validated OperationTask ot)
	{
		TqLog.getDailyLog().info("receive a task, task = {}", ot);

		rService.pubRedisTask(ot);
		return JSONMessage.createSuccess("创建任务成功，任务 = " + JSON.toJSONString(ot)).toString();
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addServer(@Validated RedisServerEntity rse)
	{
		TqLog.getDailyLog().info("add server, server = {}", rse);
		String id = rService.addServer(rse);
		
		JSONObject res = new JSONObject();
		res.put("id", id);
		
		return JSONMessage.createSuccess().addData(res).toString();
	}

	@RequestMapping(value= "/", method = RequestMethod.PUT)
	public String modifyServer(@Validated RedisServerEntity rse)
	{
		TqLog.getDailyLog().info("modify server, server = {}", rse);
		rService.modifyServer(rse);
		
		return JSONMessage.createSuccess().toString();
	}
	
	@RequestMapping(value= "/{id:.+}", method = RequestMethod.DELETE)
	public String deleteServer(@PathVariable("id") String id)
	{
		TqLog.getDailyLog().info("delete server, id = {}", id);
		//1.删除配置数据
		rService.deleteServer(id);
		//2.删除结果数据
		rService.deleteResult(id);
		
		JSONObject res = new JSONObject();
		res.put("id", id);
		
		return JSONMessage.createSuccess().addData(res).toString();
	}
	
}

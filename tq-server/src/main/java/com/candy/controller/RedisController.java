package com.candy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.service.OperationTask;
import com.candy.service.RedisService;
import com.candy.service.patrol.RedisResult;
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
	public String pubRedisTask(OperationTask ot)
	{
		TqLog.getDailyLog().info("receive a task, task = {}", ot);

		rService.pubRedisTask(ot);
		return JSONMessage.createSuccess("创建任务成功，任务 = " + JSON.toJSONString(ot)).toString();
		
	}
	
}

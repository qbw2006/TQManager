package com.candy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.service.RedisService;
import com.candy.service.manage.OperationTask;
import com.candy.service.patrol.RedisResult;
import com.candy.utils.JSONMessage;
import com.candy.utils.TqLog;

@RestController
@RequestMapping("/qtManager/redis")
public class RedisController {

	@Autowired
	private RedisService rService;
	
	@RequestMapping(value= "/health", method = RequestMethod.GET)
	public String healthInfo()
	{
		List<RedisResult> res = rService.healthInfo();
		
		return JSONMessage.createSuccess().addData(JSONObject.parseObject(JSON.toJSONString(res))).toString();
	}
	
	@RequestMapping(value= "/task", method = RequestMethod.POST)
	public String pubRedisTask(JSONObject o)
	{
		TqLog.getDailyLog().info("receive a opertaion, info = {}", o);
		
		OperationTask ot = JSONObject.toJavaObject(o, OperationTask.class);
		
		rService.pubRedisTask(ot);
		
		return JSONMessage.createSuccess().toString();
	}
	
	
}

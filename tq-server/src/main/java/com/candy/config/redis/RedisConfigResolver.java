package com.candy.config.redis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.ex.RedisConfigException;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

@Component
public class RedisConfigResolver {
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfigResolver.class);

	private final Map<String, RedisConfig> serverMap = Maps.newHashMap();

	@PostConstruct
	public void init() {
		File f = new File("conf/redis.json");
		try {
			String redisConf = FileUtils.readFileToString(f, Charsets.UTF_8);
			JSONObject all = JSONObject.parseObject(redisConf);

			RedisConfig common = all.getObject("default", RedisConfig.class);

			List<RedisConfig> servers = all.getJSONArray("servers").toJavaList(RedisConfig.class);
			
			JSONObject concern = all.getJSONObject("concern");

			assemble(common, servers, concern);

			check(servers);
			
			servers.forEach(p->{serverMap.put(p.getId(), p);});

			LOG.info("config = {}", JSON.toJSONString(this));

		} catch (IOException e) {
			LOG.error("读取配置文件异常！", e);
		} catch (RedisConfigException e) {
			LOG.error("配置异常！", e);
		}
	}

	private static void check(List<RedisConfig> servers) throws RedisConfigException {
		if (servers == null) {
			throw new RedisConfigException("配置为空");
		}
		
		for (RedisConfig r : servers) {
			//检查参数是否正确
			if (!r.isCorrect()) {
				throw new RedisConfigException("配置为错,错误配置：" + r.toString());
			}
			
			//删除集合中的空字符串和Null
			r.checkCollection();
		}
	}

	private static void assemble(RedisConfig common, List<RedisConfig> servers, JSONObject concern) {
		for (RedisConfig r : servers) {
			if (StringUtils.isEmpty(r.getRedisHost())) {
				r.setRedisHost(common.getRedisHost());
			}

			if (r.getRedisPort() < 0) {
				r.setRedisPort(common.getRedisPort());
			}

			if (StringUtils.isEmpty(r.getRedisPassword())) {
				r.setRedisPassword(common.getRedisPassword());
			}

			if (CollectionUtils.isEmpty(r.getRedisMode())) {
				r.setRedisMode(common.getRedisMode());
			}

			if (StringUtils.isEmpty(r.getServerPassword())) {
				r.setServerHost(common.getServerHost());
			}

			if (StringUtils.isEmpty(r.getServerPassword())) {
				r.setServerPassword(common.getServerPassword());
			}

			if (StringUtils.isEmpty(r.getServerUsername())) {
				r.setServerUsername(common.getServerUsername());
			}
			
			//装配redis的concernkey
			for (String key : r.getRedisMode())
			{
				List<String> c = concern.getJSONArray(key).toJavaList(String.class);
				if (!CollectionUtils.isEmpty(c))
				{
					r.addConcernKey(concern.getJSONArray(key).toJavaList(String.class));
				}
			}
		}
	}

	public Map<String, RedisConfig> getServerMap() {
		return serverMap;
	}
	
	public RedisConfig getServer(String id)
	{
		return serverMap.get(id);
	}
}

package com.candy.config.redis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.candy.ex.RedisConfigException;
import com.google.common.base.Charsets;

@Component
public class RedisConfig {
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);

	private List<RedisServer> servers;

	@PostConstruct
	public void init() {
		File f = new File("conf/redis.json");
		try {
			String redisConf = FileUtils.readFileToString(f, Charsets.UTF_8);
			JSONObject all = JSONObject.parseObject(redisConf);

			RedisServer common = all.getObject("default", RedisServer.class);

			servers = all.getJSONArray("servers").toJavaList(RedisServer.class);

			assemble(common, servers);

			check(servers);

			LOG.info("config = {}", JSON.toJSONString(this));

		} catch (IOException e) {
			LOG.error("读取配置文件异常！", e);
		} catch (RedisConfigException e) {
			LOG.error("配置异常！", e);
		}
	}

	private static void check(List<RedisServer> servers) throws RedisConfigException {
		if (servers == null) {
			throw new RedisConfigException("配置为空");
		}
		for (RedisServer r : servers) {
			if (!r.isCorrect()) {
				throw new RedisConfigException("配置为错,错误配置：" + r.toString());
			}
		}
	}

	private static void assemble(RedisServer common, List<RedisServer> servers) {
		for (RedisServer r : servers) {
			if (StringUtils.isEmpty(r.getRedisHost())) {
				r.setRedisHost(common.getRedisHost());
			}

			if (r.getRedisPort() < 0) {
				r.setRedisPort(common.getRedisPort());
			}

			if (StringUtils.isEmpty(r.getRedisPassword())) {
				r.setRedisPassword(common.getRedisPassword());
			}

			if (r.getRedisMode() < 0) {
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
		}
	}

	public List<RedisServer> getServers() {
		return servers;
	}

}

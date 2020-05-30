package com.jt.sso.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.StaticApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisPoolUtil {
	
	@Value("${redis.minIdle}")
	private int minIdle;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.clusterNodes}")
	private String clusterNodes;
	
	@Bean
	public Jedis getJedis() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxTotal(maxTotal);
		String[] node = clusterNodes.split(":");
		String host = node[0];
		int port = Integer.parseInt(node[1]);
		JedisPool jedisPool= new JedisPool(poolConfig, host, port);
		return jedisPool.getResource();
	}
	
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
}

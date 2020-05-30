package com.jt.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisClusterConfig {
	@Value("${redis.minIdle}")
	private int minIdle;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.clusterNodes}")
	private String clusterNodes;
	
	/*//redis集群
	@Bean
	public JedisCluster getJeidsCluster() {
		
		//1.定义poolconfig
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMinIdle(minIdle);
		jpc.setMaxIdle(maxIdle);
		jpc.setMaxTotal(maxTotal);
		String[] cn = clusterNodes.split(":");
		HostAndPort node = new HostAndPort(cn[0], Integer.parseInt(cn[1]));
		return new JedisCluster(node, jpc);
	}*/
	
	/*//Jedis直接连
	@Bean
	public Jedis getJedis() {
		String[] cn = clusterNodes.split(":");
		return new Jedis(cn[0], Integer.parseInt(cn[1]));
	}*/
	
	
	
	
}

package com.jt.cart.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
	private static Logger log = LoggerFactory.getLogger(TestRedis.class);
	//@Autowired(required=true)
	//private JedisCluster jedisCluster;
	@Autowired
	private Jedis jedis;
	
	
	/*@Test
	public void testRedis() {
		jedisCluster.set("测试", "成功");
		String jc = jedisCluster.get("测试redis");
		log.info("jc");
		
	}*/
	
	@Test
	public void testJedis() {
		jedis.set("测试", "成功");
		String msg = jedis.get("测试");
		log.info(msg);
	}
}

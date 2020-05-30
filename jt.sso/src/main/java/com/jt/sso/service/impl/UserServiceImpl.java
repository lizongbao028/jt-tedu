package com.jt.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.config.JedisPoolUtil;
import com.jt.sso.controler.UserController;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.Jedis;

@Service
public class UserServiceImpl implements UserService {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Jedis jedis;
	@Autowired
	private ObjectMapper objectMapper;
	/*
	 * type:1 username,2 phone, 3 email
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		String column = null;
		switch (type) {
		case 1:
			column = "username";
			break;
		case 2:
			column = "phone";
			break;
		case 3:
			column = "emali";
			break;
		}
		//如果数据库中没有记录，则返回0，有记录则不为0
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		log.info("count=" + count);
		return count == 0 ? false:true;
	}

	//用户注册
	@Override
	@Transactional//开启事务控制
	public void saveUser(User user) {
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
		
		
	}

	//用户登录
	@Override
	public String findUserByUP(User user) {
		log.info("用户信息" + user.getPassword());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDb = userMapper.selectOne(queryWrapper);
		if(userDb != null) {
			String str = "JT_TICKET" + System.currentTimeMillis() + user.getUsername();
			String token = DigestUtils.md5DigestAsHex(str.getBytes());
			try {
				String userJson = objectMapper.writeValueAsString(userDb);
				jedis.setex(token, 60 * 30, userJson);
				return token;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	
	
	
}

package com.jt.sso.controler;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import com.jt.sso.vo.SysResult;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private Jedis jedis;
	
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param,@PathVariable Integer type,String callback ){
		log.info(param +"/"+ type);
		boolean flag = userService.findCheckUser(param,type);
		
		return new JSONPObject(callback, SysResult.oK(flag));
	}
	
	/*
	 *实现用户的注册 
	 */
	@RequestMapping("/register")
	public SysResult saveUser(User user) {
		log.info("接受用户注册信息" + user);
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			log.error("注册失败", e);
		}
		return SysResult.build(201, "注册失败");
	}
	
	/**
	 *用户登录查询
	 * @param params 接受的用户参数
	 * @return
	 */
	@RequestMapping("/login")
	public SysResult findUserByUP(User user) {
		log.info("接受用户登录信息"+user);
		String token = userService.findUserByUP(user);
		if(!StringUtils.isEmpty(token)) {
			return SysResult.oK(token);
		}
		return SysResult.build(201, "登录失败");
		
	}
	
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		String json = jedis.get(ticket);
		return new JSONPObject(callback, SysResult.oK(json));
		
	}
	
}

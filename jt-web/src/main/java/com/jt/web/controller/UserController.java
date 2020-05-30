package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private Jedis jedis;
	@Autowired
	private JedisCluster jediscluster;
	/*
	 * 实现通用页面的跳转
	 * 注册/user/register.html
	 * 登录/user/login.html
	 */
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		log.info("接受用户注册信息" + user);
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			log.error("注册异常", e);
		}
		return SysResult.build(201, "用户注册失败");
	}
	
	//实现用户登录
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user,HttpServletResponse response) {
		try {
			//1.将数据发送到后台做校验
			String token = userService.findUserByUP(user);
			//2.获取token数据保存到cookie
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie =  new Cookie("JT_TICKET", token);
				cookie.setMaxAge(60 * 30);//设置cookie存活30分钟
				cookie.setPath("/");//设置cookie生效路径为根路径
				response.addCookie(cookie);
				return SysResult.oK();
			}
		} catch (Exception e) {
			log.info("web登录异常", e);
		}
		return SysResult.build(201, "用户登录失败");
	}
	
	//推出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token =null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		jedis.del(token);
		Cookie cookie = new Cookie("JT_TICKET", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/index.html";
	}
}

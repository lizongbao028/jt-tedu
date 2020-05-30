package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private HttpClientService httpClient;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		String md5Pass = DigestUtils.md5Hex(user.getPassword());
		params.put("password", md5Pass);
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		String sysJson = httpClient.doPost(url, params, null);
		try {
			SysResult sysResult = objectMapper.readValue(sysJson, SysResult.class);
			if(sysResult.getStatus() != 200) {
				throw new RuntimeException();
			}
		} catch (IOException e) {
			log.error("注册失败UserServiceimpl", e);
		}
	}

	/**
	 * 登录查询
	 */
	@Override
	public String findUserByUP(User user) {
		String url = "http://sso.jt.com/user/login";
		HashMap<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", DigestUtils.md5Hex(user.getPassword()));
		String sysJson = httpClient.doPost(url, params);
		try {
			SysResult resultJson = objectMapper.readValue(sysJson, SysResult.class);
			if(resultJson.getStatus() == 200) {
				return (String) resultJson.getData();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("登录失败");
		}
		return null;
	}
	
	
}

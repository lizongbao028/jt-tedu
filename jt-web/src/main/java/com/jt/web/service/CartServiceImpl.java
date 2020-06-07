package com.jt.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Cart;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService {

	private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class); 
	@Autowired
	private HttpClientService httpClient;
	@Autowired
	private ObjectMapper objectMapper;
	
	/*
	 * 查询购物车数据(non-Javadoc)
	 * @see com.jt.web.service.CartService#findCartListById(java.lang.Long)
	 */
	@Override
	public List<Cart> findCartListById(Long userId) {
		String url = "http://cart.jt.com/cart/query/" + userId;
		String jsonString = httpClient.doGet(url);
		List<Cart> cartList = new ArrayList<Cart>();
		try {
			SysResult sysResult = objectMapper.readValue(jsonString, SysResult.class);
			cartList = (List<Cart>)sysResult.getData();
		} catch (IOException e) {
			logger.error("--=-==-=-");
			e.printStackTrace();
		}
		return cartList;
	}

}

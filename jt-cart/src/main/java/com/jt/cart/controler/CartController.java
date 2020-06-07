package com.jt.cart.controler;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.cart.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	private static Logger logger = LoggerFactory.getLogger(CartController.class); 
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartListById(@PathVariable Long userId) {
		try {
			List<Cart> cartList = cartService.findCartListById(userId);
			System.out.println(cartList);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return SysResult.build(201, "购物车查询失败！");
		
	}
	
}

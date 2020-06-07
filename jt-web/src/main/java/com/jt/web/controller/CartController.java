package com.jt.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.po.Cart;
import com.jt.web.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	/*
	 *购物车呈现 
	 * 根据用户id查询购物车信息
	 */
	@RequestMapping("/show")
	public String findCartListById(Model model) {
		Long userId = 7L;
		List<Cart> cartList = cartService.findCartListById(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
		
	}
}

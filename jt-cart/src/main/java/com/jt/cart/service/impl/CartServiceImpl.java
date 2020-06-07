package com.jt.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper CartMapper;
	@Override
	public List<Cart> findCartListById(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		List<Cart> cartList = CartMapper.selectList(queryWrapper);
		return cartList;
	}

}

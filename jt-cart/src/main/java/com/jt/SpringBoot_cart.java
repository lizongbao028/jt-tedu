package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.cart.mapper")
public class SpringBoot_cart {
	private static Logger log = LoggerFactory.getLogger(SpringBoot_cart.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot_cart.class, args);
		log.info("==========购物车系统启动成功=============");
	}
}

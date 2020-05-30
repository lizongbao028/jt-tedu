package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.sso.mapper")
public class SpringBoot_sso {
	private static Logger log = LoggerFactory.getLogger(SpringBoot_sso.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot_sso.class, args);
		log.info("单点登录系统启动成功");
	}
}

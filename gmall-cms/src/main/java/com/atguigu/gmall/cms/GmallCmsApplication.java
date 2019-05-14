package com.atguigu.gmall.cms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
整合dubbo ；
整合mybatis-plus
 */
@EnableDubbo
@MapperScan(basePackages = "com.atguigu.gmall.cms.mapper")
@SpringBootApplication
public class GmallCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallCmsApplication.class, args);
	}

}

package com.smily.mybatis.config;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;

// @Configuration
// @ConfigurationProperties(prefix = "context")
// @PropertySource("classpath:mybatis.properties")
public class MyContext extends Context {

	public MyContext(ModelType defaultModelType) {
		super(defaultModelType);
		// TODO Auto-generated constructor stub
	}

}

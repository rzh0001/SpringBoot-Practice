package com.smily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.smily.service.AutoConfigProperties;
import com.smily.service.ValueConfigProperties;

@SpringBootApplication
public class Chapter2Application {

//	@Autowired
//	AutoConfigProperties autoConfigProperties;
//
//	@Autowired
//	ValueConfigProperties valueConfigProperties;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Chapter2Application.class, args);

		AutoConfigProperties autoConfigProperties = (AutoConfigProperties) ctx.getBean("autoConfigProperties");
		System.out.println(autoConfigProperties.toString());

		ValueConfigProperties valueConfigProperties = ctx.getBean(ValueConfigProperties.class);
		System.out.println(valueConfigProperties.toString());
	}
}

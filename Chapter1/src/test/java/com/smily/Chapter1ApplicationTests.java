package com.smily;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter1ApplicationTests {

	@Autowired
	ApplicationContext ctx;

	@Test
	public void contextLoads() {
		assertThat(ctx).isNotNull();
		assertThat(ctx.containsBean("helloController")).isTrue();
	}

}

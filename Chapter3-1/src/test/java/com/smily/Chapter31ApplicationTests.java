package com.smily;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smily.domain.User;
import com.smily.domain.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter31ApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
		userMapper.insert("AAA", 20);
		User u = userMapper.findByName("AAA");

		assertEquals(20, u.getAge().intValue());
//		Assert.assertEquals(20, u.getAge().intValue());
	}

}

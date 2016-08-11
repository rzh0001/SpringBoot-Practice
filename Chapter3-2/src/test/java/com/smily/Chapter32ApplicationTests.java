package com.smily;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smily.dbserv.bean.Users;
import com.smily.dbserv.bean.UsersExample;
import com.smily.dbserv.mapper.UsersMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter32ApplicationTests {

	@Autowired
	private UsersMapper usersMapper;

	@Test
	public void test() {
		List<Users> result = usersMapper.selectByExample(new UsersExample());
		for (Users users : result) {
			System.out.println(users.toString());
		}
	}
}

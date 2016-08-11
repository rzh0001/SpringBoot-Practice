package com.smily;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smily.mybatis.MyBatisGeneratorExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MBGMain {
	@Autowired
	MyBatisGeneratorExecutor g = new MyBatisGeneratorExecutor();

	@Test
	public void test() {
		try {
			g.execute();
		} catch (IOException | XMLParserException | InvalidConfigurationException | SQLException
				| InterruptedException e) {
			e.printStackTrace();
		}
	}
}

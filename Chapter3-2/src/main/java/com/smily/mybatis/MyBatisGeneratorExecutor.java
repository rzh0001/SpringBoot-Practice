package com.smily.mybatis;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smily.mybatis.config.MybatisGeneratorProperties;

/**
 * 完全使用Java配置的MBG执行器
 * 
 * @author Smily
 */
@Component
public class MyBatisGeneratorExecutor {
	private final static Log logger = LogFactory.getLog(MyBatisGeneratorExecutor.class);

	private MyBatisGenerator generator;

	private Configuration config = new Configuration();

	private List<String> warnings = new ArrayList();

	@Autowired
	private MybatisGeneratorProperties mybatisGeneratorProperties;

	@PostConstruct
	public void init() {
		logger.info(this.getClass().getName() + " init ...");

		Context context = new Context(null);
		context.setId("myContext");
		context.setTargetRuntime("MyBatis3");

		for (String plugin : mybatisGeneratorProperties.getPlugins()) {
			PluginConfiguration pluginConfiguration = new PluginConfiguration();
			pluginConfiguration.setConfigurationType(plugin);
			context.addPluginConfiguration(pluginConfiguration);
		}

		context.setJdbcConnectionConfiguration(mybatisGeneratorProperties.getJdbcConnection());
		context.setJavaTypeResolverConfiguration(mybatisGeneratorProperties.getJavaTypeResolver());
		context.setJavaModelGeneratorConfiguration(mybatisGeneratorProperties.getJavaModelGenerator());
		context.setJavaClientGeneratorConfiguration(mybatisGeneratorProperties.getJavaClientGenerator());
		context.setSqlMapGeneratorConfiguration(mybatisGeneratorProperties.getSqlMapGenerator());

		List<TableConfiguration> list = context.getTableConfigurations();
		list.clear();
		for (String table : mybatisGeneratorProperties.getTables()) {
			logger.info("table = {" + table + "}");
			TableConfiguration tableConfig = new TableConfiguration(context);
			String schema = null;

			if (table.contains(".")) {
				String[] sepTable = table.split("\\.");
				schema = sepTable[0];
				table = sepTable[1];
			}

			if (StringUtils.isNotEmpty(schema)) {
				tableConfig.setSchema(schema);
			} else {
//					tableConfig.setSchema(jdbc.getUserId());
				tableConfig.setSchema(mybatisGeneratorProperties.getSchema());
			}

			tableConfig.setTableName(table);
			tableConfig.addProperty("ignoreQualifiersAtRuntime", "true");

			list.add(tableConfig);
		}
		config.getContexts().add(context);

		try {
			generator = new MyBatisGenerator(config, new DefaultShellCallback(true), warnings);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void execute()
			throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {

		File java = new File(mybatisGeneratorProperties.getJavaPath(),
				mybatisGeneratorProperties.getBasePackage().replace(".", File.separator));
		File bean = new File(java, mybatisGeneratorProperties.getBeanPackage());

		if (mybatisGeneratorProperties.isCleanAll()) {
			FileUtils.deleteDirectory(bean);
			FileUtils.deleteDirectory(new File(java, mybatisGeneratorProperties.getMapperPackage()));
		}

		File resources = new File(mybatisGeneratorProperties.getResourcesPath(),
				mybatisGeneratorProperties.getBasePackage().replace(".", File.separator));
		FileUtils.deleteDirectory(new File(resources, mybatisGeneratorProperties.getMapperPackage()));

		generator.generate(new GeneratorProgressCallback());
		for (String warning : warnings) {
			logger.warn(warning);
		}
		warnings.clear();
	}

}
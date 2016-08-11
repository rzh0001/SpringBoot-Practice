package com.smily.mybatis.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "generator")
@PropertySource("classpath:mybatis.properties")
public class MybatisGeneratorProperties {

	List<String> plugins;

	String basePackage;

	String beanPackage;

	String mapperPackage;

	String javaPath;

	String resourcesPath;

	boolean forceBigDecimals;

	boolean cleanAll;

	String schema;

	String[] tables;

	private JDBCConnectionConfiguration jdbcConnection;

	private JavaModelGeneratorConfiguration javaModelGenerator;

	private JavaTypeResolverConfiguration javaTypeResolver;

	private JavaClientGeneratorConfiguration javaClientGenerator;

	private SqlMapGeneratorConfiguration sqlMapGenerator;

	public List<String> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<String> plugins) {
		this.plugins = plugins;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public String getJavaPath() {
		return javaPath;
	}

	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}

	public String getResourcesPath() {
		return resourcesPath;
	}

	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}

	public boolean isForceBigDecimals() {
		return forceBigDecimals;
	}

	public void setForceBigDecimals(boolean forceBigDecimals) {
		this.forceBigDecimals = forceBigDecimals;
	}

	public boolean isCleanAll() {
		return cleanAll;
	}

	public void setCleanAll(boolean cleanAll) {
		this.cleanAll = cleanAll;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}

	public JDBCConnectionConfiguration getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JDBCConnectionConfiguration jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}

	public JavaModelGeneratorConfiguration getJavaModelGenerator() {
		return javaModelGenerator;
	}

	public void setJavaModelGenerator(JavaModelGeneratorConfiguration javaModelGenerator) {
		this.javaModelGenerator = javaModelGenerator;
	}

	public JavaTypeResolverConfiguration getJavaTypeResolver() {
		return javaTypeResolver;
	}

	public void setJavaTypeResolver(JavaTypeResolverConfiguration javaTypeResolver) {
		this.javaTypeResolver = javaTypeResolver;
	}

	public JavaClientGeneratorConfiguration getJavaClientGenerator() {
		return javaClientGenerator;
	}

	public void setJavaClientGenerator(JavaClientGeneratorConfiguration javaClientGenerator) {
		this.javaClientGenerator = javaClientGenerator;
	}

	public SqlMapGeneratorConfiguration getSqlMapGenerator() {
		return sqlMapGenerator;
	}

	public void setSqlMapGenerator(SqlMapGeneratorConfiguration sqlMapGenerator) {
		this.sqlMapGenerator = sqlMapGenerator;
	}

	@Override
	public String toString() {
		return "MybatisGeneratorProperties [plugins=" + plugins + ", basePackage=" + basePackage + ", beanPackage="
				+ beanPackage + ", mapperPackage=" + mapperPackage + ", javaPath=" + javaPath + ", resourcesPath="
				+ resourcesPath + ", forceBigDecimals=" + forceBigDecimals + ", cleanAll=" + cleanAll + ", schema="
				+ schema + ", tables=" + Arrays.toString(tables) + ", jdbcConnection=" + jdbcConnection
				+ ", javaModelGenerator=" + javaModelGenerator + ", javaTypeResolver=" + javaTypeResolver
				+ ", javaClientGenerator=" + javaClientGenerator + ", sqlMapGenerator=" + sqlMapGenerator + "]";
	}

}

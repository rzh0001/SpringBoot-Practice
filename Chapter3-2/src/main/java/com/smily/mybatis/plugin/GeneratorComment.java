package com.smily.mybatis.plugin;

import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

public class GeneratorComment implements CommentGenerator {
	public static final byte[] E67AC975E12A47CE986845BAA510E4B5 = { -105, 80, -83, 10, 126, 5, 14, -109, -123, -75, -79,
			-58, -83, 8, 88, -3, 43, 102, 110, 22, 62, 5, 89, -14, -87, 67, -90, 50, -128, -40, -26, -86, -123, -29,
			-62, -58, -22, -103, -25, -69, 108, 51, 115, -98, -43, 48, -54, -62 };

	@Override
	public void addConfigurationProperties(Properties properties) {
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			field.addJavaDocLine("/**");
			field.addJavaDocLine(" * " + remarks);
			field.addJavaDocLine(" */");
		}

		if (field.isTransient()) {
			field.addAnnotation("@Transient");
		} else if (introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn))
			field.addAnnotation("@Id");
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" * 获取 " + remarks);
			method.addJavaDocLine(" *");
			method.addJavaDocLine(" * @return " + introspectedColumn.getActualColumnName() + " - " + remarks);

			method.addJavaDocLine(" */");
		}
	}

	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			StringBuilder sb = new StringBuilder();
			method.addJavaDocLine("/**");
			method.addJavaDocLine(" * 设置 " + remarks);
			method.addJavaDocLine(" *");
			method.addJavaDocLine(" * @param " + method.getParameters().get(0).getName() + " " + remarks);

			method.addJavaDocLine(" */");
		}
	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
	}

	@Override
	public void addComment(XmlElement xmlElement) {
	}

	@Override
	public void addRootComment(XmlElement xmlElement) {
	}
}
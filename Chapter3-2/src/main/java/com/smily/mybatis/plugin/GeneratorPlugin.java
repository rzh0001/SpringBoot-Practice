package com.smily.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;

public class GeneratorPlugin extends PluginAdapter {
	public static final String INDENT_BLANK = "        ";
	private boolean isTable;
	private String rootClass;
	private List<IntrospectedColumn> keys;
	private List<IntrospectedColumn> columns;

	@Override
	public void setContext(Context context) {
		super.setContext(context);
		CommentGeneratorConfiguration commentConfiguration = new CommentGeneratorConfiguration();
		commentConfiguration.setConfigurationType(GeneratorComment.class.getCanonicalName());
		context.setCommentGeneratorConfiguration(commentConfiguration);
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		for (IntrospectedColumn c : introspectedTable.getAllColumns()) {
			c.setJavaProperty(getPropertyName(c.getJavaProperty()));
		}

		rootClass = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getProperty("rootClass");

		if (rootClass == null) {
			rootClass = introspectedTable.getTableConfigurationProperty("rootClass");
		} else {
			isTable = true;
		}

		keys = introspectedTable.getPrimaryKeyColumns();
		columns = introspectedTable.getNonBLOBColumns();
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return generateModelCode(introspectedTable, topLevelClass, false);
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		return generateModelCode(introspectedTable, topLevelClass, false);
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return generateModelCode(introspectedTable, topLevelClass, true);
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		interfaze.addAnnotation("@Mapper");
		interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));

		FullyQualifiedJavaType root = new FullyQualifiedJavaType("com.smily.mybatis.dbserv.TableMapper");
		interfaze.addImportedType(root);
		interfaze.addSuperInterface(root);
		return true;
	}

	private boolean generateModelCode(IntrospectedTable introspectedTable, TopLevelClass topLevelClass, boolean isKey) {
		String thisClass = topLevelClass.getType().getShortName();
		List<IntrospectedColumn> columns = (isKey) ? keys : this.columns;

		boolean isAllString = true;
		boolean hasDate = false;
		for (IntrospectedColumn c : columns) {
			FullyQualifiedJavaType type = c.getFullyQualifiedJavaType();
			if (!(type.equals(FullyQualifiedJavaType.getStringInstance()))) {
				if (type.equals(FullyQualifiedJavaType.getDateInstance())) {
					hasDate = true;
				}
				isAllString = false;
			}
		}

		topLevelClass.addImportedType("java.util.Map");
		topLevelClass.addImportedType("javax.persistence.Table");
		topLevelClass.addImportedType("javax.persistence.Id");
		topLevelClass.addImportedType("javax.persistence.Id");
		topLevelClass.addImportedType("org.apache.commons.lang3.builder.ToStringBuilder");
		topLevelClass.addImportedType("org.apache.commons.lang3.builder.ToStringStyle");

		if (isTable) {
			topLevelClass.addImportedType(rootClass);
		}

		if (isKey) {
			topLevelClass.addAnnotation(new StringBuilder().append("@Table(name = \"")
					.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()).append("\")").toString());
		}

		if (isTable) {
			Field field = new Field();
			field.setVisibility(JavaVisibility.PRIVATE);
			field.setStatic(true);
			field.setFinal(true);
			field.setType(new FullyQualifiedJavaType("long"));
			field.setName("serialVersionUID");
			field.setInitializationString("1L");

			topLevelClass.addField(field);
		}

		StringBuilder sb = new StringBuilder();

		String enumName = "Column";
		InnerEnum enumClass = new InnerEnum(new FullyQualifiedJavaType(enumName));

		enumClass.setVisibility(JavaVisibility.PUBLIC);
		for (IntrospectedColumn c : columns) {
			enumClass.addEnumConstant(c.getActualColumnName());
		}

		Method method = new Method("parse");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setStatic(true);
		method.setReturnType(enumClass.getType());
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "column"));

		method.addBodyLine("try {");
		method.addBodyLine(new StringBuilder().append("return ").append(enumName)
				.append(".valueOf(column.toUpperCase());").toString());
		method.addBodyLine("} catch (Exception e) {");
		method.addBodyLine("return null;");
		method.addBodyLine("}");

		enumClass.addMethod(method);

		topLevelClass.addInnerEnum(enumClass);

		method = new Method("contains");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "column"));

		if ((isTable) && (introspectedTable.isJava5Targeted())) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine(
				new StringBuilder().append("return ").append(enumName).append(".parse(column) != null;").toString());

		topLevelClass.addMethod(method);

		method = new Method("get");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.addParameter(new Parameter(enumClass.getType(), "column"));

		if (!(isAllString)) {
			method.addBodyLine("Object value;");
		}

		method.addBodyLine("if (column == null) return null;");
		method.addBodyLine("switch (column) {");
		for (IntrospectedColumn c : columns) {
			FullyQualifiedJavaType type = c.getFullyQualifiedJavaType();
			sb.setLength(0);
			sb.append("case ").append(c.getActualColumnName()).append(": {");
			method.addBodyLine(sb.toString());

			sb.setLength(0);
			sb.append("return ");
			if (type.equals(FullyQualifiedJavaType.getStringInstance())) {
				sb.append(getter(c)).append(";");
			} else {
				sb.append("(value = ").append(getter(c)).append(") == null ? null : ");
				if (type.equals(FullyQualifiedJavaType.getDateInstance()))
					sb.append("formatDate((Date) value)");
				else {
					sb.append("value.toString()");
				}
				sb.append(";");
			}
			method.addBodyLine(sb.toString());

			method.addBodyLine("}");
		}
		method.addBodyLine("default: {");
		method.addBodyLine("return null;");
		method.addBodyLine("}}");

		topLevelClass.addMethod(method);

		method = new Method("get");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "column"));

		if ((isTable) && (introspectedTable.isJava5Targeted())) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine(
				new StringBuilder().append("return get(").append(enumName).append(".parse(column));").toString());

		topLevelClass.addMethod(method);

		method = new Method("get");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("Map<String, String>"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, String>"), "map"));

		if ((isTable) && (introspectedTable.isJava5Targeted())) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine("for (Column c: Column.values()) {");
		method.addBodyLine("map.put(c.toString(), get(c));");
		method.addBodyLine("}");
		method.addBodyLine("return map;");

		topLevelClass.addMethod(method);

		method = new Method("set");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(topLevelClass.getType());
		method.addParameter(new Parameter(enumClass.getType(), "column"));
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "value"));

		method.addBodyLine("if (column == null) return this;");
		method.addBodyLine("switch (column) {");
		boolean noStringUtils = true;
		for (IntrospectedColumn c : columns) {
			FullyQualifiedJavaType type = c.getFullyQualifiedJavaType();
			sb.setLength(0);
			sb.append("case ").append(c.getActualColumnName()).append(": {");
			method.addBodyLine(sb.toString());

			sb.setLength(0);
			if (type.equals(FullyQualifiedJavaType.getStringInstance())) {
				sb.append(setter(c));
				sb.append("value");
			} else if (type.equals(FullyQualifiedJavaType.getDateInstance())) {
				sb.append(setter(c));
				sb.append("parseDate(value)");
			} else {
				if (noStringUtils) {
					topLevelClass.addImportedType("org.apache.commons.lang3.StringUtils");
					noStringUtils = false;
				}
				method.addBodyLine("value = StringUtils.stripToNull(value);");
				sb.append(setter(c));
				sb.append("value == null ? null : ");
				sb.append("new ").append(type.getShortName()).append("(value)");
			}
			sb.append(");");

			method.addBodyLine(sb.toString());
			method.addBodyLine("break;");
			method.addBodyLine("}");
		}
		method.addBodyLine("default: {");
		method.addBodyLine("break;");
		method.addBodyLine("}}");
		method.addBodyLine("return this;");

		topLevelClass.addMethod(method);

		method = new Method("set");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(topLevelClass.getType());
		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "column"));

		method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "value"));

		if ((isTable) && (introspectedTable.isJava5Targeted())) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine(new StringBuilder().append("return set(").append(enumName).append(".parse(column), value);")
				.toString());

		topLevelClass.addMethod(method);

		method = new Method("set");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(topLevelClass.getType());
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, ?>"), "map"));

		if ((isTable) && (introspectedTable.isJava5Targeted())) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine("for (Column c: Column.values()) {");
		method.addBodyLine("if (map.containsKey(c.toString())) {");
		method.addBodyLine("Object v = map.get(c.toString());");
		if (hasDate) {
			method.addBodyLine("if (v instanceof Date) {");
			method.addBodyLine("set(c, formatDate((Date) v));");
			method.addBodyLine("} else {");
		}
		method.addBodyLine("set(c, v == null ? null : v.toString());");
		if (hasDate) {
			method.addBodyLine("}");
		}
		method.addBodyLine("}");
		method.addBodyLine("}");
		method.addBodyLine("return this;");

		topLevelClass.addMethod(method);

		if (isTable) {
			method = new Method("set");

			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(topLevelClass.getType());
			method.addParameter(new Parameter(new FullyQualifiedJavaType(rootClass), "table"));
			if (introspectedTable.isJava5Targeted()) {
				method.addAnnotation("@Override");
			}

			method.addBodyLine("return set(table.get());");

			topLevelClass.addMethod(method);
		}

		if ((isKey) || (keys.size() <= 1)) {
			topLevelClass.addImportedType("org.apache.commons.lang3.builder.EqualsBuilder");
			topLevelClass.addImportedType("org.apache.commons.lang3.builder.HashCodeBuilder");

			method = new Method("toKey");

			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(FullyQualifiedJavaType.getStringInstance());
			if ((isTable) && (introspectedTable.isJava5Targeted())) {
				method.addAnnotation("@Override");
			}

			method.addBodyLine("StringBuilder sb = new StringBuilder();");
			for (IntrospectedColumn c : keys) {
				method.addBodyLine(new StringBuilder().append("sb.append(").append(getter(c)).append(").append(\"|\");")
						.toString());
			}

			method.addBodyLine("return sb.toString();");

			topLevelClass.addMethod(method);

			method = new Method("equals");

			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
			method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "obj"));

			if (introspectedTable.isJava5Targeted()) {
				method.addAnnotation("@Override");
			}

			method.addBodyLine("if (obj == null) {");
			method.addBodyLine("return false;");
			method.addBodyLine("} else if (obj == this) {");
			method.addBodyLine("return true;");
			method.addBodyLine(new StringBuilder().append("} else if (!(obj instanceof ").append(thisClass)
					.append(")) {").toString());
			method.addBodyLine("return false;");
			method.addBodyLine("}");

			method.addBodyLine(new StringBuilder().append(thisClass).append(" rhs = (").append(thisClass)
					.append(") obj;").toString());
			method.addBodyLine("return new EqualsBuilder()");
			for (IntrospectedColumn c : keys) {
				method.addBodyLine(new StringBuilder().append("        .append(").append(getter(c)).append(", rhs.")
						.append(getter(c)).append(")").toString());
			}
			method.addBodyLine("        .isEquals();");

			topLevelClass.addMethod(method);

			method = new Method("hashCode");

			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(FullyQualifiedJavaType.getIntInstance());
			if (introspectedTable.isJava5Targeted()) {
				method.addAnnotation("@Override");
			}

			method.addBodyLine("return new HashCodeBuilder()");
			for (IntrospectedColumn c : keys) {
				method.addBodyLine(
						new StringBuilder().append("        .append(").append(getter(c)).append(")").toString());
			}
			method.addBodyLine("        .toHashCode();");

			topLevelClass.addMethod(method);
		}

		method = new Method("toString");

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		if (introspectedTable.isJava5Targeted()) {
			method.addAnnotation("@Override");
		}

		method.addBodyLine("ToStringBuilder builder =");
		method.addBodyLine("        new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);");

		for (IntrospectedColumn c : columns) {
			method.addBodyLine(new StringBuilder().append("if (").append(getter(c)).append(" != null) {").toString());
			method.addBodyLine(new StringBuilder().append("builder.append(\"").append(c.getJavaProperty())
					.append("\", ").append(getter(c)).append(");").toString());

			method.addBodyLine("}");
		}
		method.addBodyLine("return builder.toString();");

		topLevelClass.addMethod(method);

		method = new Method(topLevelClass.getType().getShortName());

		method.setConstructor(true);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addBodyLine("/* pass */");

		topLevelClass.addMethod(method);

		method = new Method(topLevelClass.getType().getShortName());

		method.setConstructor(true);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, ?>"), "map"));

		method.addBodyLine("set(map);");

		topLevelClass.addMethod(method);

		if (isTable) {
			method = new Method(topLevelClass.getType().getShortName());

			method.setConstructor(true);
			method.setVisibility(JavaVisibility.PUBLIC);
			method.addParameter(new Parameter(new FullyQualifiedJavaType(rootClass), "table"));
			method.addBodyLine("set(table);");

			topLevelClass.addMethod(method);
		}

		if (isKey) {
			method = new Method(topLevelClass.getType().getShortName());

			method.setConstructor(true);
			method.setVisibility(JavaVisibility.PUBLIC);

			for (IntrospectedColumn c : columns) {
				String property = c.getJavaProperty();
				method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), property));

				sb.setLength(0);
				sb.append(setter(c));
				if (c.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getStringInstance())) {
					sb.append(property);
				} else {
					sb.append("new ").append(c.getFullyQualifiedJavaType().getShortName());
					sb.append("(").append(property).append(")");
				}
				sb.append(");");
				method.addBodyLine(sb.toString());
			}

			topLevelClass.addMethod(method);
		}

		return true;
	}

	private static String getter(IntrospectedColumn column) {
		return new StringBuilder().append("get").append(firstUpper(column.getJavaProperty())).append("()").toString();
	}

	private static String setter(IntrospectedColumn column) {
		return new StringBuilder().append("set").append(firstUpper(column.getJavaProperty())).append("(").toString();
	}

	private static String getPropertyName(String name) {
		return getPropertyName(name, false);
	}

	private static String getPropertyName(String name, boolean firstUpper) {
		StringBuilder sb = new StringBuilder();

		for (String t : name.split("_")) {
			if ((firstUpper) || (sb.length() > 0))
				sb.append(firstUpper(t));
			else {
				sb.append(t);
			}
		}

		return sb.toString();
	}

	private static String firstUpper(String string) {
		return ((string.isEmpty()) ? string
				: new StringBuilder().append(string.substring(0, 1).toUpperCase()).append(string.substring(1))
						.toString());
	}
}

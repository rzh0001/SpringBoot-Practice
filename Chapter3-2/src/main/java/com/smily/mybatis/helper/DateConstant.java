package com.smily.mybatis.helper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class DateConstant {
	public static final String DATE_TIME = "yyyyMMddHHmmss";
	public static final String DATE = "yyyyMMdd";
	public static final String TIME = "HHmmss";
	public static final String[] FORMATS = { "yyyyMMddHHmmss", "yyyyMMdd", "HHmmss" };

	public static final Set<String> FORMAT_SET = new LinkedHashSet(Arrays.asList(FORMATS));

	public static SimpleDateFormat createDateTimeFormat() {
		return new SimpleDateFormat("yyyyMMddHHmmss");
	}

	public static SimpleDateFormat createDateFormat() {
		return new SimpleDateFormat("yyyyMMdd");
	}

	public static SimpleDateFormat createTimeFormat() {
		return new SimpleDateFormat("HHmmss");
	}
}
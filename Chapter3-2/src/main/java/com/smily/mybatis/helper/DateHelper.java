package com.smily.mybatis.helper;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateHelper {
	public static Date from(String string) {
		return from(string, DateConstant.FORMATS);
	}

	public static Date from(String string, String[] formats) {
		for (String format : formats) {
			Date date = from(string, format);
			if (date != null) {
				return date;
			}
		}
		return null;
	}

	public static Date from(String string, String format) {
		if (DateConstant.FORMAT_SET.contains(format)) {
			int length = format.length();
			if (length < string.length()) {
				string = string.substring(0, length);
			}
		}
		return new SimpleDateFormat(format).parse(string, new ParsePosition(0));
	}

	public static Date fromDateTime(String string) {
		return from(string, "yyyyMMddHHmmss");
	}

	public static Date fromDate(String string) {
		return from(string, "yyyyMMdd");
	}

	public static Date fromTime(String string) {
		return from(string, "HHmmss");
	}

	public static String to(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String to(Date date, DateFormat format) {
		return format.format(date);
	}

	public static String toDateTime() {
		return toDateTime(new Date());
	}

	public static String toDate() {
		return toDate(new Date());
	}

	public static String toTime() {
		return toTime(new Date());
	}

	public static String toDateTime(Date date) {
		return to(date, "yyyyMMddHHmmss");
	}

	public static String toDate(Date date) {
		return to(date, "yyyyMMdd");
	}

	public static String toTime(Date date) {
		return to(date, "HHmmss");
	}
}
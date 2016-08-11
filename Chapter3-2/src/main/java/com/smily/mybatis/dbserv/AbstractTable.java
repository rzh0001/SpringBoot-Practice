package com.smily.mybatis.dbserv;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smily.mybatis.helper.DateHelper;

public abstract class AbstractTable implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract boolean contains(String paramString);

	public abstract String get(String paramString);

	public abstract Map<String, String> get(Map<String, String> paramMap);

	public Map<String, String> get() {
		return get(new LinkedHashMap());
	}

	public abstract AbstractTable set(String paramString1, String paramString2);

	public abstract AbstractTable set(Map<String, ?> paramMap);

	public abstract AbstractTable set(AbstractTable paramAbstractTable);

	public abstract String toKey();

	public AbstractTable fromMap(Map<String, ?> map) {
		return set(map);
	}

	public Map<String, String> toMap() {
		return get();
	}

	protected static Date parseDate(String string) {
		return DateHelper.from(string, new String[] { "yyyyMMddHHmmss", "yyyyMMdd" });
	}

	protected static String formatDate(Date date) {
		return DateHelper.toDate(date);
	}

}
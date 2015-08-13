package com.insaic.common.code.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, NE
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static List<SearchFilter> parse(Map<String, Object> searchParams) {
		List<SearchFilter> filters = Lists.newArrayList();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			Object value = entry.getValue();
			if (StringUtils.isBlank(value.toString())) {
				continue;
			}
			String[] names = StringUtils.split(entry.getKey(), "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}
			SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), value);
			filters.add(filter);
		}
		
		return filters ;
	}


}

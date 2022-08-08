package com.codirect.codiappapi.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.util.ObjectUtils;

public abstract class ObjectUtil {

	public static Long toLong(Object value) {
		return ObjectUtils.isEmpty(value) ? null : Long.valueOf(value.toString());
	}
	
	public static boolean isAllNull(Object ...values) {
		return !Stream.of(values).anyMatch(o -> !ObjectUtils.isEmpty(o));
	}
	
	public static boolean isNull(Object o) {
		return ObjectUtils.isEmpty(o);
	}
	
	public static List<String> toList(String text, String regex) {
		if (text.isEmpty()) return new LinkedList<>();
		return Arrays.asList(text.split(regex));
	}
}

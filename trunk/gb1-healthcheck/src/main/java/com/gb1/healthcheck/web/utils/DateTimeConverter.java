package com.gb1.healthcheck.web.utils;

public class DateTimeConverter extends DateConverter {
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

	public DateTimeConverter() {
		super(DEFAULT_DATE_TIME_FORMAT);
	}
}

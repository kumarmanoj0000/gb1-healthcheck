package com.gb1.healthcheck.web.meals;

import com.gb1.healthcheck.web.utils.DateConverter;

public class MealInstantConverter extends DateConverter {
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

	public MealInstantConverter() {
		super(DEFAULT_DATE_TIME_FORMAT);
	}
}

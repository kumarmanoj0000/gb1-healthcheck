package com.gb1.healthcheck.web.meals;

import com.gb1.healthcheck.web.utils.DateConverter;

public class MealInstantConverter extends DateConverter {
	public MealInstantConverter() {
		super("yyyy-MM-dd HH:mm");
	}
}

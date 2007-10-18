package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;

public interface MealPropertyProvider extends MealMutablePropertyProvider {
	Date getInstant();
}

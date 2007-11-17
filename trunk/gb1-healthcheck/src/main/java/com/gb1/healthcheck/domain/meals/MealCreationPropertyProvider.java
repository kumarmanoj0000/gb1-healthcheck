package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.Set;

import com.gb1.healthcheck.domain.users.User;

public interface MealCreationPropertyProvider {
	User getEater();

	Date getInstant();

	Set<PreparedFood> getDishes();
}

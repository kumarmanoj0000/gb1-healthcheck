package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.Set;

public interface MealPropertyProvider {
	Date getInstant();

	Set<PreparedFood> getDishes();
}

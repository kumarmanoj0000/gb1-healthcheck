package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface SimpleFoodMutablePropertyProvider {
	String getName();

	Group getGroup();

	Set<Nutrient> getNutrients();
}

package com.gb1.healthcheck.services.meals;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.meals.Meal;

public class FullMealHydrater implements Hydrater<Meal> {
	public FullMealHydrater() {
	}

	public Meal hydrate(Meal meal) {
		// calling .size() will force loading of meal dishes
		meal.getDishes().size();
		return meal;
	}
}

package com.gb1.healthcheck.services.meals;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.services.Hydrater;

public class FullMealHydrater implements Hydrater<Meal> {
	public FullMealHydrater() {
	}

	public Meal hydrate(Meal meal) {
		// calling .size() will force loading of meal dishes
		meal.getDishes().size();
		return meal;
	}
}

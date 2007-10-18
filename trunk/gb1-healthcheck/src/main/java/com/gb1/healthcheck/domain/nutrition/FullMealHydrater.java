package com.gb1.healthcheck.domain.nutrition;

import com.gb1.commons.dataaccess.Hydrater;

public class FullMealHydrater implements Hydrater<Meal> {
	public FullMealHydrater() {
	}

	public Meal hydrate(Meal meal) {
		// calling .size() will force loading of meal dishes
		meal.getDishes().size();
		return meal;
	}
}

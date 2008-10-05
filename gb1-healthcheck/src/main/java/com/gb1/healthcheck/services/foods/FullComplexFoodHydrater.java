package com.gb1.healthcheck.services.foods;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.services.Hydrater;

public class FullComplexFoodHydrater implements Hydrater<ComplexFood> {
	public ComplexFood hydrate(ComplexFood food) {
		// calling .size() will force loading of complex food ingredients
		food.getIngredients().size();
		return food;
	}
}

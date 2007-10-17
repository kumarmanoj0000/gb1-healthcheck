package com.gb1.healthcheck.domain.nutrition;

import com.gb1.commons.dataaccess.Hydrater;

public class FullComplexFoodHydrater implements Hydrater<ComplexFood> {
	public ComplexFood hydrate(ComplexFood food) {
		// calling .size() will force loading of complex food ingredients
		food.getIngredients().size();
		return food;
	}
}

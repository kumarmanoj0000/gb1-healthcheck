package com.gb1.healthcheck.services.foods;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;

@Component("simpleFoodAssembler")
public class SimpleFoodAssemblerImpl implements SimpleFoodAssembler {
	public SimpleFoodAssemblerImpl() {
	}

	public SimpleFood create(SimpleFoodCreationRequest request) {
		SimpleFood food = new SimpleFood(request.getName(), request.getFoodGroup());
		for (Nutrient n : request.getNutrients()) {
			food.addNutrient(n);
		}

		return food;
	}

	public void update(SimpleFood food, SimpleFoodUpdateRequest request) {
		food.setName(request.getName());
		food.setFoodGroup(request.getFoodGroup());

		food.clearNutrients();
		for (Nutrient n : request.getNutrients()) {
			food.addNutrient(n);
		}
	}
}

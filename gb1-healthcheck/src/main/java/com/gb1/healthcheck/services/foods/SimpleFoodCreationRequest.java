package com.gb1.healthcheck.services.foods;

import java.util.Set;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;

public interface SimpleFoodCreationRequest {
	String getName();

	FoodGroup getFoodGroup();

	Set<Nutrient> getNutrients();
}

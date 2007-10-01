package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Food;

public class ComplexFoodCreateRequest {
	private String name;
	private Set<Food> ingredients = new HashSet<Food>();

	public ComplexFoodCreateRequest() {
	}

	public String getName() {
		return name;
	}

	public Set<Food> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}
}

package com.gb1.healthcheck.web.nutrition;

import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.Food;

public class CreateComplexFoodRequest implements ComplexFoodPropertyProvider {
	private String name;

	public CreateComplexFoodRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Food> getIngredients() {
		return null;
	}

	public void setSelectedIngredientIds(String[] ingredientIds) {
	}
}

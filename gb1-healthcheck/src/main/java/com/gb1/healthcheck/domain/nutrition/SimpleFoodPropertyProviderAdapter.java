package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public class SimpleFoodPropertyProviderAdapter implements SimpleFoodPropertyProvider {
	private SimpleFoodCreationRequest request;

	public SimpleFoodPropertyProviderAdapter(SimpleFoodCreationRequest request) {
		this.request = request;
	}

	public FoodGroup getFoodGroup() {
		return request.getFoodGroup();
	}

	public String getName() {
		return request.getName();
	}

	public Set<Nutrient> getNutrients() {
		return request.getNutrients();
	}
}

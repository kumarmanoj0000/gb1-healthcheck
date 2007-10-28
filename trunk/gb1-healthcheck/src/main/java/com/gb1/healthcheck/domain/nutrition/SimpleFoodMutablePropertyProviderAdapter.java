package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public class SimpleFoodMutablePropertyProviderAdapter implements SimpleFoodMutablePropertyProvider {
	private SimpleFoodUpdateRequest request;

	public SimpleFoodMutablePropertyProviderAdapter(SimpleFoodUpdateRequest request) {
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

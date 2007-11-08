package com.gb1.healthcheck.web.meals;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.meals.MealCreationRequest;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.meals.PreparedFoodCreationRequest;

public class BasicMealCreationRequest extends MealRequestSupport implements MealCreationRequest {
	public BasicMealCreationRequest() {
	}

	public Set<PreparedFoodCreationRequest> getDishCreationRequests() {
		final Set<PreparedFoodCreationRequest> requests = new HashSet<PreparedFoodCreationRequest>();
		final Long[] selectedFoodIds = getSelectedFoodIds();
		final String[] selectedPrepMethodNames = getSelectedPreparationMethodNames();

		for (int i = 0; i < selectedFoodIds.length; i++) {
			final int idx = i;
			requests.add(new PreparedFoodCreationRequest() {
				public Long getIngredientId() {
					return selectedFoodIds[idx];
				}

				public PreparationMethod getPreparationMethod() {
					return PreparationMethod.valueOf(selectedPrepMethodNames[idx]);
				}
			});
		}

		return requests;
	}
}

package com.gb1.healthcheck.web.nutrition;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealUpdateRequest;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.domain.nutrition.PreparedFoodUpdateRequest;

public class BasicMealUpdateRequest extends MealRequestSupport implements MealUpdateRequest {
	public BasicMealUpdateRequest(Meal meal) {
		setInstant(meal.getInstant());
		setDishes(meal.getDishes());
	}

	public Set<PreparedFoodUpdateRequest> getDishUpdateRequests() {
		Set<PreparedFoodUpdateRequest> requests = new HashSet<PreparedFoodUpdateRequest>();
		final Long[] selectedFoodIds = getSelectedFoodIds();
		final String[] selectedPrepMethodNames = getSelectedPreparationMethodNames();

		for (int i = 0; i < selectedFoodIds.length; i++) {
			final int idx = i;
			requests.add(new PreparedFoodUpdateRequest() {
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

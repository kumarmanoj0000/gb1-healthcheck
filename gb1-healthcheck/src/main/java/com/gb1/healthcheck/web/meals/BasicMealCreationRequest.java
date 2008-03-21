package com.gb1.healthcheck.web.meals;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

public class BasicMealCreationRequest extends MealRequestSupport implements MealCreationRequest {
	public BasicMealCreationRequest(User eater) {
		super(eater);
	}

	public Set<PreparedFoodCreationRequest> getDishCreationRequests() {
		Set<PreparedFoodCreationRequest> requests = new HashSet<PreparedFoodCreationRequest>();
		Long[] selectedFoodIds = getSelectedFoodIds();
		String[] selectedPrepMethodNames = getSelectedPreparationMethodNames();

		for (int i = 0; i < selectedFoodIds.length; i++) {
			int idx = i;
			requests.add(new PreparedFoodCreationAndUpdateRequest(selectedFoodIds[idx],
					selectedPrepMethodNames[idx]));
		}

		return requests;
	}
}

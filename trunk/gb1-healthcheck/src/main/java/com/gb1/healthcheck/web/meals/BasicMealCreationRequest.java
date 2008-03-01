package com.gb1.healthcheck.web.meals;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

public class BasicMealCreationRequest extends MealRequestSupport implements MealCreationRequest {
	private Long eaterId;

	public BasicMealCreationRequest(User eater) {
		eaterId = eater.getId();
	}

	public Long getEaterId() {
		return eaterId;
	}

	public void setEaterId(Long eaterId) {
		this.eaterId = eaterId;
	}

	public Set<PreparedFoodCreationRequest> getDishCreationRequests() {
		final Set<PreparedFoodCreationRequest> requests = new HashSet<PreparedFoodCreationRequest>();
		final Long[] selectedFoodIds = getSelectedFoodIds();
		final String[] selectedPrepMethodNames = getSelectedPreparationMethodNames();

		for (int i = 0; i < selectedFoodIds.length; i++) {
			final int idx = i;
			requests.add(new PreparedFoodCreationAndUpdateRequest(selectedFoodIds[idx],
					selectedPrepMethodNames[idx]));
		}

		return requests;
	}
}

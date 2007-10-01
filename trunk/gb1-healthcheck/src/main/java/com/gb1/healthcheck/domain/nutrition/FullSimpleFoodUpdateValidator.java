package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public class FullSimpleFoodUpdateValidator implements SimpleFoodUpdateValidator {
	private FoodRepository foodRepo;

	public FullSimpleFoodUpdateValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		isFoodNameAlreadyTaken(food);
	}

	private void isFoodNameAlreadyTaken(SimpleFood food) throws FoodAlreadyExistsException {
		// make sure that no other food has the same name, aside from the validated food itself

		boolean nameAlreadyTaken;
		Set<Food> foodsWithName = foodRepo.findFoodsByName(food.getName());

		if (foodsWithName.isEmpty()) {
			nameAlreadyTaken = false;
		}
		else if (foodsWithName.size() == 1) {
			Food foodWithSameName = foodsWithName.iterator().next();
			nameAlreadyTaken = !foodWithSameName.getId().equals(food.getId());
		}
		else {
			nameAlreadyTaken = true;
		}

		if (nameAlreadyTaken) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

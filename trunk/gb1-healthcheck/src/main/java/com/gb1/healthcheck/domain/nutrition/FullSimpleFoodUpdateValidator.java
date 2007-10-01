package com.gb1.healthcheck.domain.nutrition;

public class FullSimpleFoodUpdateValidator implements SimpleFoodUpdateValidator {
	private FoodRepository foodRepo;

	public FullSimpleFoodUpdateValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		Food foodWithName = foodRepo.findFoodByName(food.getName());
		if (foodWithName != null && !foodWithName.getId().equals(food.getId())) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

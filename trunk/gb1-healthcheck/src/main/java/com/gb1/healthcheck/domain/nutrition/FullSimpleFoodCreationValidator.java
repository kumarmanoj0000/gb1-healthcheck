package com.gb1.healthcheck.domain.nutrition;

public class FullSimpleFoodCreationValidator implements SimpleFoodCreationValidator {
	private FoodRepository foodRepo;

	public FullSimpleFoodCreationValidator() {
	}

	public void validate(SimpleFood food) throws FoodException {
		if (foodRepo.findFoodByName(food.getName()) != null) {
			throw new FoodAlreadyExistsException(food.getName());
		}
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

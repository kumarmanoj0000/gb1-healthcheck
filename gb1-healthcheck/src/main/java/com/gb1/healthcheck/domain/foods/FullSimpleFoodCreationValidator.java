package com.gb1.healthcheck.domain.foods;

public class FullSimpleFoodCreationValidator implements SimpleFoodValidator {
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

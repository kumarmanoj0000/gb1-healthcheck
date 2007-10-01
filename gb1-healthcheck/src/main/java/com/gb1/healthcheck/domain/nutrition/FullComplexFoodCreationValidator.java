package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public class FullComplexFoodCreationValidator implements ComplexFoodValidator {
	private FoodRepository foodRepo;

	public FullComplexFoodCreationValidator() {
	}

	public void validate(ComplexFood food) throws FoodException {
		if (isFoodNameAlreadyTaken(food)) {
			throw new FoodAlreadyExistsException(food.getName());
		}
		if (foodHasNoIngredients(food)) {
			throw new ComplexFoodHasNoIngredientsException();
		}
	}

	private boolean isFoodNameAlreadyTaken(ComplexFood food) {
		Set<Food> foodsWithSameName = foodRepo.findFoodsByName(food.getName());
		return !foodsWithSameName.isEmpty();
	}

	private boolean foodHasNoIngredients(ComplexFood food) {
		return food.getIngredients().isEmpty();
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

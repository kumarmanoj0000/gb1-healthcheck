package com.gb1.healthcheck.domain.foods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("complexFoodCreationValidator")
public class ComplexFoodCreationValidator implements Validator<ComplexFood, FoodException> {
	@Resource
	protected FoodRepository foodRepo;

	public ComplexFoodCreationValidator() {
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
		List<Food> foodsWithSameName = foodRepo.findFoodsByName(food.getName());
		return !foodsWithSameName.isEmpty();
	}

	private boolean foodHasNoIngredients(ComplexFood food) {
		return food.getIngredients().isEmpty();
	}
}

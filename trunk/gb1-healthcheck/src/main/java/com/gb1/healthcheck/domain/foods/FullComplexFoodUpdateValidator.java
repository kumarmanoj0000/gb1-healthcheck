package com.gb1.healthcheck.domain.foods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("complexFoodUpdateValidator")
public class FullComplexFoodUpdateValidator implements Validator<ComplexFood, FoodException> {
	@Resource
	protected FoodRepository foodRepo;

	public FullComplexFoodUpdateValidator() {
	}

	public void validate(ComplexFood food) throws FoodException {
		if (isNameAlreadyTaken(food)) {
			throw new FoodAlreadyExistsException(food.getName());
		}
		if (foodHasNoIngredients(food)) {
			throw new ComplexFoodHasNoIngredientsException();
		}
	}

	private boolean isNameAlreadyTaken(ComplexFood food) {
		boolean taken;
		List<Food> foodsWithSameName = foodRepo.findFoodsByName(food.getName());

		if (foodsWithSameName.isEmpty()) {
			taken = false;
		}
		else if (foodsWithSameName.size() == 1) {
			taken = foodsWithSameName.get(0).equals(food) ? false : true;
		}
		else {
			taken = true;
		}

		return taken;
	}

	private boolean foodHasNoIngredients(ComplexFood food) {
		return food.getIngredients().isEmpty();
	}
}

package com.gb1.healthcheck.services.foods;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodRepository;

@Component("complexFoodAssembler")
public class ComplexFoodAssemblerImpl implements ComplexFoodAssembler {
	@Resource
	protected FoodRepository foodRepo;

	public ComplexFoodAssemblerImpl() {
	}

	public ComplexFood createComplexFood(ComplexFoodCreationRequest request) {
		ComplexFood food = new ComplexFood();
		food.setName(request.getName());

		for (Long ingredientId : request.getIngredientIds()) {
			food.addIngredient(foodRepo.loadFood(ingredientId));
		}

		return food;
	}

	public void updateComplexFood(ComplexFood food, ComplexFoodUpdateRequest request) {
		food.setName(request.getName());

		food.clearIngredients();
		for (Long ingredientId : request.getIngredientIds()) {
			food.addIngredient(foodRepo.loadFood(ingredientId));
		}
	}
}

package com.gb1.healthcheck.services.foods;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodRepository;

@Component("complexFoodAssembler")
public class ComplexFoodAssemblerImpl implements ComplexFoodAssembler {
	private FoodRepository foodRepo;

	public ComplexFoodAssemblerImpl() {
	}

	public ComplexFood create(ComplexFoodCreationRequest request) {
		ComplexFood food = new ComplexFood(request.getName());
		for (Long ingredientId : request.getIngredientIds()) {
			food.addIngredient(foodRepo.loadFood(ingredientId));
		}

		return food;
	}

	public void update(ComplexFood food, ComplexFoodUpdateRequest request) {
		food.setName(request.getName());

		food.clearIngredients();
		for (Long ingredientId : request.getIngredientIds()) {
			food.addIngredient(foodRepo.loadFood(ingredientId));
		}
	}

	@Resource
	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

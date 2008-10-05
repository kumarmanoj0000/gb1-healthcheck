package com.gb1.healthcheck.web.meals;

import java.io.Serializable;

import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.services.foods.FoodService;

public class PreparedFoodBuilder implements Serializable {
	private Long ingredientId;
	private String preparationMethodName;

	public PreparedFoodBuilder() {
	}

	public PreparedFoodBuilder(PreparedFood dish) {
		ingredientId = dish.getIngredient().getId();
		preparationMethodName = dish.getPreparationMethod().name();
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getPreparationMethodName() {
		return preparationMethodName;
	}

	public void setPreparationMethodName(String prepMethodName) {
		this.preparationMethodName = prepMethodName;
	}

	public PreparedFood build(FoodService foodService) {
		return new PreparedFood(foodService.getFood(ingredientId), PreparationMethod
				.valueOf(preparationMethodName));
	}
}

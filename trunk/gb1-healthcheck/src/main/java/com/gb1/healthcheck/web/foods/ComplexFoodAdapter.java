package com.gb1.healthcheck.web.foods;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.services.foods.FoodService;

public class ComplexFoodAdapter {
	private ComplexFood food;
	private FoodService foodSvc;

	ComplexFoodAdapter(ComplexFood food) {
		this(food, null);
	}

	public ComplexFoodAdapter(ComplexFood food, FoodService foodSvc) {
		this.food = food;
		this.foodSvc = foodSvc;
	}

	public String getName() {
		return food.getName();
	}

	public void setName(String name) {
		food.setName(name);
	}

	public Long[] getIngredientIds() {
		Set<Long> ids = new HashSet<Long>();
		for (Food ingredient : food.getIngredients()) {
			ids.add(ingredient.getId());
		}
		return ids.toArray(new Long[0]);
	}

	public void setIngredientIds(Long[] ids) {
		food.clearIngredients();
		food.addIngredients(foodSvc.getFoods(Arrays.asList(ids)));
	}

	public ComplexFood getTarget() {
		return food;
	}
}

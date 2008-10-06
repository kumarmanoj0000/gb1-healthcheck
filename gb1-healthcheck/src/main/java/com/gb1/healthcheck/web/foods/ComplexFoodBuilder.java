package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.services.foods.FoodService;

public class ComplexFoodBuilder {
	private ComplexFood source;
	private String name;
	private List<Long> ingredientIds = new ArrayList<Long>();

	public ComplexFoodBuilder(ComplexFood source) {
		this.source = source;
		this.name = source.getName();

		for (Food ingredient : source.getIngredients()) {
			ingredientIds.add(ingredient.getId());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long[] getIngredientIds() {
		return ingredientIds.toArray(new Long[0]);
	}

	public void setIngredientIds(Long[] ids) {
		ingredientIds.clear();
		ingredientIds.addAll(Arrays.asList(ids));
	}

	public ComplexFood build(FoodService foodSvc) {
		source.setName(name);
		source.clearIngredients();
		source.addIngredients(foodSvc.getFoods(ingredientIds));

		return source;
	}
}

package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;

public class ComplexFoodCreationRequest {
	private FoodRepository foodRepo;
	private String name;
	private Set<Long> ingredientIds = new HashSet<Long>();

	public ComplexFoodCreationRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Food> getIngredients() {
		Set<Food> ingredients = new HashSet<Food>();
		for (Long foodId : ingredientIds) {
			ingredients.add(foodRepo.loadFood(foodId));
		}

		return Collections.unmodifiableSet(ingredients);
	}

	public String[] getSelectedIngredientIds() {
		String[] selectedIngredientIds = new String[ingredientIds.size()];

		int i = 0;
		for (Long ingredientId : ingredientIds) {
			selectedIngredientIds[i++] = ingredientId.toString();
		}

		return selectedIngredientIds;
	}

	public void setSelectedIngredientIds(String[] selectedIngredientIds) {
		ingredientIds.clear();
		for (String id : selectedIngredientIds) {
			ingredientIds.add(Long.parseLong(id));
		}
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

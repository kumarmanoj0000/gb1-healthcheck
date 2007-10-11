package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;

public abstract class ComplexFoodRequestSupport implements ComplexFoodMutablePropertyProvider {
	private FoodRepository foodRepo;
	private String name;
	private Set<Long> selectedIngredientIds = new HashSet<Long>();

	public ComplexFoodRequestSupport() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Food> getIngredients() {
		Set<Food> ingredients = new HashSet<Food>();
		for (Long foodId : selectedIngredientIds) {
			ingredients.add(foodRepo.loadFood(foodId));
		}

		return Collections.unmodifiableSet(ingredients);
	}

	protected void setIngredients(Set<Food> ingredients) {
		selectedIngredientIds.clear();
		for (Food f : ingredients) {
			selectedIngredientIds.add(f.getId());
		}
	}

	public Long[] getSelectedIngredientIds() {
		Long[] ids = new Long[selectedIngredientIds.size()];

		int i = 0;
		for (Long ingredientId : selectedIngredientIds) {
			ids[i++] = ingredientId;
		}

		return ids;
	}

	public void setSelectedIngredientIds(Long[] ids) {
		selectedIngredientIds.clear();
		for (Long id : ids) {
			selectedIngredientIds.add(id);
		}
	}

	public void setFoodRepository(FoodRepository foodRepo) {
		this.foodRepo = foodRepo;
	}
}

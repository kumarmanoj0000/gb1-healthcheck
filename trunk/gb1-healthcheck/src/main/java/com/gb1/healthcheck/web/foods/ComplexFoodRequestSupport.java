package com.gb1.healthcheck.web.foods;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.foods.Food;

public abstract class ComplexFoodRequestSupport {
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

	public Set<Long> getIngredientIds() {
		return Collections.unmodifiableSet(selectedIngredientIds);
	}

	protected void setIngredients(Set<Food> ingredients) {
		selectedIngredientIds.clear();
		for (Food f : ingredients) {
			selectedIngredientIds.add(f.getId());
		}
	}

	public Long[] getSelectedIngredientIds() {
		return selectedIngredientIds.toArray(new Long[0]);
	}

	public void setSelectedIngredientIds(Long[] ids) {
		selectedIngredientIds.clear();
		for (Long id : ids) {
			selectedIngredientIds.add(id);
		}
	}
}

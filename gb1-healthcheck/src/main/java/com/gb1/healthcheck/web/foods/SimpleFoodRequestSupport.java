package com.gb1.healthcheck.web.foods;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public abstract class SimpleFoodRequestSupport {
	private String name;
	private FoodGroup foodGroup;
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	public SimpleFoodRequestSupport() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodGroup getFoodGroup() {
		return foodGroup;
	}

	@TypeConversion(converter = "com.opensymphony.xwork2.util.EnumTypeConverter")
	public void setFoodGroup(FoodGroup foodGroup) {
		this.foodGroup = foodGroup;
	}

	public void setSelectedNutrients(String[] nutrientNames) {
		nutrients.clear();
		for (String nutrientName : nutrientNames) {
			nutrients.add(Nutrient.valueOf(nutrientName));
		}
	}

	public String[] getSelectedNutrients() {
		String[] nutrientNames = new String[nutrients.size()];
		int i = 0;

		for (Nutrient n : nutrients) {
			nutrientNames[i++] = n.name();
		}

		return nutrientNames;
	}

	public Set<Nutrient> getNutrients() {
		return Collections.unmodifiableSet(nutrients);
	}

	protected void setNutrients(Set<Nutrient> nutrients) {
		this.nutrients.clear();
		this.nutrients.addAll(nutrients);
	}
}

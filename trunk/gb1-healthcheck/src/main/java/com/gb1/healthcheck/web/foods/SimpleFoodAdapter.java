package com.gb1.healthcheck.web.foods;

import java.util.ArrayList;
import java.util.List;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Nutrient;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

public class SimpleFoodAdapter {
	private SimpleFood food;

	public SimpleFoodAdapter(SimpleFood food) {
		this.food = food;
	}

	public SimpleFood getTarget() {
		return food;
	}

	public String getName() {
		return food.getName();
	}

	public void setName(String name) {
		food.setName(name);
	}

	public FoodGroup getFoodGroup() {
		return food.getFoodGroup();
	}

	@TypeConversion(converter = "com.opensymphony.xwork2.util.EnumTypeConverter")
	public void setFoodGroup(FoodGroup foodGroup) {
		food.setFoodGroup(foodGroup);
	}

	public String[] getNutrients() {
		List<String> nutrientNames = new ArrayList<String>();
		for (Nutrient n : food.getNutrients()) {
			nutrientNames.add(n.name());
		}

		return (String[]) nutrientNames.toArray();
	}

	public void setNutrients(String[] nutrientNames) {
		food.clearNutrients();
		for (String nutrientName : nutrientNames) {
			food.addNutrient(Nutrient.valueOf(nutrientName));
		}
	}
}

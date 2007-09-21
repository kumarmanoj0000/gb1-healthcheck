package com.gb1.healthcheck.web.nutrition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class FoodAction {
	private FoodService foodService;

	public String listFoods() {
		return Action.SUCCESS;
	}

	public List<SimpleFood> getSimpleFoodList() {
		List<SimpleFood> sortedFoods = new ArrayList<SimpleFood>(foodService.getSimpleFoods());
		Collections.sort(sortedFoods, new Food.ByNameComparator());

		return sortedFoods;
	}

	public List<ComplexFood> getComplexFoodList() {
		List<ComplexFood> sortedFoods = new ArrayList<ComplexFood>(foodService.getComplexFoods());
		Collections.sort(sortedFoods, new Food.ByNameComparator());

		return sortedFoods;
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}

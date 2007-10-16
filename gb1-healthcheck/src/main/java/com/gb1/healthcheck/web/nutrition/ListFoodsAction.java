package com.gb1.healthcheck.web.nutrition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gb1.commons.dao.NullHydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;

public class ListFoodsAction {
	private FoodService foodService;

	private List<SimpleFood> simpleFoods;
	private List<ComplexFood> complexFoods;

	public ListFoodsAction() {
	}

	public String listFoods() {
		simpleFoods = new ArrayList<SimpleFood>(foodService.getSimpleFoods());
		Collections.sort(simpleFoods, new Food.ByNameComparator());

		complexFoods = new ArrayList<ComplexFood>(foodService
				.getComplexFoods(new NullHydrater<ComplexFood>()));
		Collections.sort(complexFoods, new Food.ByNameComparator());

		return Action.SUCCESS;
	}

	public List<SimpleFood> getSimpleFoods() {
		return simpleFoods;
	}

	public List<ComplexFood> getComplexFoods() {
		return complexFoods;
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}

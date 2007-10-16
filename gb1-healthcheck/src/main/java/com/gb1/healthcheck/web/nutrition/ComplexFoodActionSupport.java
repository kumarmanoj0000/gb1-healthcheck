package com.gb1.healthcheck.web.nutrition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gb1.commons.dao.NullHydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class ComplexFoodActionSupport extends ActionSupport implements Preparable {
	private FoodService foodService;
	private List<Food> availableIngredients = new ArrayList<Food>();

	public void prepare() {
		availableIngredients.clear();
		availableIngredients.addAll(getFoodService().getSimpleFoods());
		availableIngredients.addAll(getFoodService().getComplexFoods(
				new NullHydrater<ComplexFood>()));
		Collections.sort(availableIngredients, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<Food> getAvailableIngredients() {
		return Collections.unmodifiableList(availableIngredients);
	}

	protected FoodService getFoodService() {
		return foodService;
	}

	public void setFoodService(FoodService foodSvc) {
		this.foodService = foodSvc;
	}
}

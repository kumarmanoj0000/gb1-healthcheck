package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MealActionSupport extends ActionSupport implements Preparable {
	private FoodService foodService;
	private MealService mealService;

	private List<Food> availableFoods = new LinkedList<Food>();

	protected MealActionSupport() {
	}

	public void prepare() {
		availableFoods.addAll(getFoodService().getSimpleFoods());
		availableFoods
				.addAll(getFoodService().getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		List<PreparationMethod> methods = Arrays.asList(PreparationMethod.values());
		return Collections.unmodifiableList(methods);
	}

	public List<Food> getAvailableFoods() {
		return Collections.unmodifiableList(availableFoods);
	}

	protected FoodService getFoodService() {
		return foodService;
	}

	public void setFoodService(FoodService foodSvc) {
		this.foodService = foodSvc;
	}

	protected MealService getMealService() {
		return mealService;
	}

	public void setMealService(MealService mealSvc) {
		this.mealService = mealSvc;
	}
}

package com.gb1.healthcheck.web.nutrition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class ListMealsAction {
	private List<Meal> mealHistory = new LinkedList<Meal>();
	private MealService mealService;

	public ListMealsAction() {
	}

	public String listMeals() {
		mealHistory.addAll(mealService.getMealHistory());
		return Action.SUCCESS;
	}

	public List<Meal> getMealHistory() {
		return Collections.unmodifiableList(mealHistory);
	}

	public void setMealService(MealService mealService) {
		this.mealService = mealService;
	}
}

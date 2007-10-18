package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.services.nutrition.MealService;
import com.opensymphony.xwork2.Action;

public class DeleteMealAction {
	private MealService mealSvc;
	private Long mealId;

	public DeleteMealAction() {
	}

	public String delete() {
		mealSvc.deleteMeal(mealId);
		return Action.SUCCESS;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public void setMealService(MealService mealSvc) {
		this.mealSvc = mealSvc;
	}
}

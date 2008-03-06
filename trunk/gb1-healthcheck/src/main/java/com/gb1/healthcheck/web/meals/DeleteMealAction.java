package com.gb1.healthcheck.web.meals;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;

@Controller("deleteMealAction")
@Scope("prototype")
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

	@Resource
	public void setMealService(MealService mealSvc) {
		this.mealSvc = mealSvc;
	}
}

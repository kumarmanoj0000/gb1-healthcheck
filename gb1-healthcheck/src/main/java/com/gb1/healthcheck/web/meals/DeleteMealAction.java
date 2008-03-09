package com.gb1.healthcheck.web.meals;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("deleteMealAction")
@Scope("prototype")
public class DeleteMealAction extends ActionSupport {
	private MealService mealSvc;
	private Long mealId;
	private String confirmationMessage;

	public DeleteMealAction() {
	}

	public String delete() {
		mealSvc.deleteMeal(mealId);
		confirmationMessage = getText("meals.delete.success");
		return Action.SUCCESS;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	@Resource
	public void setMealService(MealService mealSvc) {
		this.mealSvc = mealSvc;
	}
}

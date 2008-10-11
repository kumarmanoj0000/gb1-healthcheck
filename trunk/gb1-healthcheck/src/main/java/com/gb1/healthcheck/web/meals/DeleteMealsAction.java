package com.gb1.healthcheck.web.meals;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.services.meals.MealService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(type = FlashResult.class, value = "manageMeals", params = { "namespace", "/meals", "parse",
		"true", "actionMessages", "${actionMessages}", "refreshList", "true" })
public class DeleteMealsAction extends ActionSupport {
	@Resource
	protected MealService mealService;

	private Long[] mealIds;

	public DeleteMealsAction() {
	}

	@Override
	public String execute() {
		if (mealIds != null) {
			mealService.deleteMeals(Arrays.asList(mealIds));
			addActionMessage(getText("meals.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setMealIds(Long[] mealIds) {
		this.mealIds = mealIds;
	}
}

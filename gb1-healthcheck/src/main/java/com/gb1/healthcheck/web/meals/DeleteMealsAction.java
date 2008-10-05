package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
			Set<Long> idsToDelete = new HashSet<Long>();
			idsToDelete.addAll(Arrays.asList(mealIds));

			mealService.deleteMeals(idsToDelete);
			addActionMessage(getText("meals.delete.success"));
		}

		return Action.SUCCESS;
	}

	public void setMealIds(Long[] mealIds) {
		this.mealIds = mealIds;
	}
}

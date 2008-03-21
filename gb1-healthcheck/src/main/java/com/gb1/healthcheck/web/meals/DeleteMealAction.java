package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("deleteMealAction")
@Scope("prototype")
@ParentPackage("default")
@Result(type = ServletActionRedirectResult.class, value = "listMeals", params = { "namespace",
		"/meals", "parse", "true", "actionMessageKey", "${actionMessageKey}" })
public class DeleteMealAction extends MealActionSupport {
	public DeleteMealAction() {
	}

	@Override
	public String execute() {
		getMealService().deleteMeal(getMealId());
		setActionMessageKey("meals.delete.success");

		return Action.SUCCESS;
	}
}

package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/editMeal.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "manageMeals", params = {
				"namespace", "/meals", "parse", "true", "actionMessageKey", "${actionMessageKey}",
				"refreshList", "true" }) })
public class UpdateMealAction extends MealActionSupport {
	private BasicMealUpdateRequest model;

	public UpdateMealAction() {
	}

	@Override
	public String input() {
		Meal meal = getMealService().loadMeal(getMealId(), new FullMealHydrater());
		model = new BasicMealUpdateRequest(meal);

		return Action.INPUT;
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			getMealService().updateMeal(getModel());

			setActionMessageKey("meals.edit.success");
			result = Action.SUCCESS;
		}
		catch (MealException e) {
			addActionError(getText("meals.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public Long getEaterId() {
		return getRequester().getId();
	}

	public BasicMealUpdateRequest getModel() {
		return model;
	}

	public void setModel(BasicMealUpdateRequest model) {
		this.model = model;
	}
}

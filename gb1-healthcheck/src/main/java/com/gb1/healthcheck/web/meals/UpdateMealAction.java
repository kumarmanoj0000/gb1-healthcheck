package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/editMeal.jsp"),
		@Result(type = FlashResult.class, value = "manageMeals", params = { "namespace", "/meals",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
public class UpdateMealAction extends MealActionSupport {
	private Long mealId;

	public UpdateMealAction() {
	}

	@Override
	public String input() {
		MealBuilder model = new MealBuilder(mealService.getMeal(mealId, new FullMealHydrater()));
		saveModel(model);

		return Action.INPUT;
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			mealService.updateMeal(getModel().build(foodService));

			unloadModel();
			addActionMessage(getText("meals.edit.success"));
			result = Action.SUCCESS;
		}
		catch (MealException e) {
			addActionError(getText("meals.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
}

package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/editMeal.jsp"),
		@Result(type = FlashResult.class, value = "manageMeals", params = { "namespace", "/meals",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class CreateMealAction extends MealActionSupport {
	public CreateMealAction() {
	}

	@Override
	public String input() {
		MealBuilder model = new MealBuilder(new Meal().setEater(requester));
		saveModel(model);

		return Action.INPUT;
	}

	@Override
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "model.instant", message = "", key = "meals.edit.error.instantRequired") })
	public String execute() {
		String result = Action.INPUT;

		try {
			mealService.createMeal(getModel().build(foodService));

			unloadModel();
			addActionMessage(getText("meals.edit.success"));
			result = Action.SUCCESS;
		}
		catch (MealAlreadyExistsException e) {
			addFieldError("model.instant", getText("meal.error.alreadyExists"));
		}
		catch (MealException e) {
			addActionError(getText("meals.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}
}

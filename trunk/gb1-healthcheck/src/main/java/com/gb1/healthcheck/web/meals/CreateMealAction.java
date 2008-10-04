package com.gb1.healthcheck.web.meals;

import java.util.Map;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.struts2.dispatcher.FlashResult;
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
public class CreateMealAction extends MealActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = CreateMealAction.class.getName() + ".model";

	private Map<String, Object> sessionMap;

	public CreateMealAction() {
	}

	@Override
	public String input() {
		sessionMap.put(MODEL_SESSION_KEY, new MealBuilder(new Meal().setEater(getRequester())));
		return Action.INPUT;
	}

	@Override
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "model.instant", message = "", key = "meals.edit.error.instantRequired") })
	public String execute() {
		String result = Action.INPUT;

		try {
			mealService.createMeal(getModel().build(foodService));
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

	public MealBuilder getModel() {
		return (MealBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}

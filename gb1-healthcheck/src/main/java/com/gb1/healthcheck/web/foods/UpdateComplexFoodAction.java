package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.services.foods.FullComplexFoodHydrater;
import com.gb1.struts2.dispatcher.FlashResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Results( {
		@Result(name = "input", value = "/views/foods/editComplexFood.jsp"),
		@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class UpdateComplexFoodAction extends ComplexFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateComplexFoodAction.class.getName()
			+ ".model";
	private Map<String, Object> sessionMap;

	public UpdateComplexFoodAction() {
	}

	@Override
	public String input() {
		ComplexFood food = foodService.getComplexFood(getFoodId(), new FullComplexFoodHydrater());
		sessionMap.put(MODEL_SESSION_KEY, new ComplexFoodAdapter(food, foodService));

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.complexFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			foodService.updateComplexFood(getModel().getTarget());
			sessionMap.remove(MODEL_SESSION_KEY);

			addActionMessage(getText("foods.complexFoods.edit.success"));
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.complexFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public ComplexFoodAdapter getModel() {
		return (ComplexFoodAdapter) sessionMap.get(MODEL_SESSION_KEY);
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}

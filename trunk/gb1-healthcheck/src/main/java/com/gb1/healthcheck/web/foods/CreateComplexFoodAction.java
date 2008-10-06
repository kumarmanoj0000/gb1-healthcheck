package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;
import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodHasNoIngredientsException;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/foods/editComplexFood.jsp"),
		@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class CreateComplexFoodAction extends ComplexFoodActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = CreateComplexFoodAction.class.getName()
			+ ".model";

	private Map<String, Object> sessionMap;

	public CreateComplexFoodAction() {
	}

	@Override
	public String input() throws Exception {
		sessionMap.put(MODEL_SESSION_KEY, new ComplexFoodBuilder(new ComplexFood()));
		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.complexFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			foodService.createComplexFood(getModel().build(foodService));

			sessionMap.remove(MODEL_SESSION_KEY);
			addActionMessage(getText("foods.complexFoods.edit.success"));
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (ComplexFoodHasNoIngredientsException e) {
			addFieldError("model.name", getText("food.exception.selectAtLeastOneIngredient"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.complexFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public ComplexFoodBuilder getModel() {
		return (ComplexFoodBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}
}

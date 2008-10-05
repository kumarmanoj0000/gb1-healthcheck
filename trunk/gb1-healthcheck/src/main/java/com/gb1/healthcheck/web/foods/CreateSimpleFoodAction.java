package com.gb1.healthcheck.web.foods;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.FlashResult;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/foods/editSimpleFood.jsp"),
		@Result(type = FlashResult.class, value = "manageFoods", params = { "namespace", "/foods",
				"parse", "true", "actionMessages", "${actionMessages}", "refreshList", "true" }) })
@Validation
public class CreateSimpleFoodAction extends SimpleFoodActionSupport {
	protected SimpleFoodAdapter model = new SimpleFoodAdapter(new SimpleFood());

	public CreateSimpleFoodAction() {
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.simpleFoods.edit.error.nameRequired") })
	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			foodService.createSimpleFood(getModel().getTarget());
			addActionMessage(getText("foods.simpleFoods.edit.success"));
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.simpleFoods.edit.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public SimpleFoodAdapter getModel() {
		return model;
	}
}

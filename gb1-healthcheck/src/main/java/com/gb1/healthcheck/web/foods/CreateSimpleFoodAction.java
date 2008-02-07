package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class CreateSimpleFoodAction extends SimpleFoodActionSupport {
	private BasicSimpleFoodCreationRequest foodCreationRequest = new BasicSimpleFoodCreationRequest();

	public CreateSimpleFoodAction() {
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "Name is required.") })
	public String submit() throws FoodException {
		String result;

		try {
			getFoodService().createSimpleFood(foodCreationRequest);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
			result = Action.INPUT;
		}

		return result;
	}

	public BasicSimpleFoodCreationRequest getModel() {
		return foodCreationRequest;
	}
}

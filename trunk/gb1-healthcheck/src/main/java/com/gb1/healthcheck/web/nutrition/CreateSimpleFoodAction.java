package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.opensymphony.xwork2.Action;

public class CreateSimpleFoodAction extends SimpleFoodActionSupport {
	private BasicSimpleFoodCreationRequest foodCreationRequest = new BasicSimpleFoodCreationRequest();

	public CreateSimpleFoodAction() {
	}

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

package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.ComplexFoodHasNoIngredientsException;
import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.opensymphony.xwork2.Action;

public class CreateComplexFoodAction extends ComplexFoodActionSupport {
	private ComplexFoodCreationRequest foodCreationRequest = new ComplexFoodCreationRequest();

	public CreateComplexFoodAction() {
	}

	public String submit() {
		String result = Action.INPUT;

		try {
			getFoodService().createComplexFood(foodCreationRequest);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
		}
		catch (ComplexFoodHasNoIngredientsException e) {
			addFieldError("model.name", "You must select at least one ingredient.");
		}
		catch (FoodException e) {
			addActionError(e.getMessage());
		}

		return result;
	}

	public ComplexFoodCreationRequest getModel() {
		return foodCreationRequest;
	}
}

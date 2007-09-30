package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.List;

import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.Group;
import com.gb1.healthcheck.domain.nutrition.Nutrient;
import com.gb1.healthcheck.services.nutrition.FoodService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class CreateSimpleFoodAction extends ActionSupport {
	private FoodService foodService;
	private SimpleFoodCreationRequest foodCreationRequest = new SimpleFoodCreationRequest();

	public CreateSimpleFoodAction() {
	}

	public String input() {
		return Action.SUCCESS;
	}

	public String submit() throws FoodException {
		String result;

		try {
			foodService.createSimpleFood(foodCreationRequest);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
			result = Action.INPUT;
		}

		return result;
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public SimpleFoodCreationRequest getModel() {
		return foodCreationRequest;
	}

	public List<Group> getAvailableGroups() {
		return Arrays.asList(Group.values());
	}

	public List<Nutrient> getAvailableNutrients() {
		return Arrays.asList(Nutrient.values());
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
}

package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.opensymphony.xwork2.Action;

public class CreateMealAction extends MealActionSupport {
	private MealCreationRequest model = new MealCreationRequest();

	public CreateMealAction() {
	}

	public String input() {
		return Action.SUCCESS;
	}

	public String submit() {
		String result = Action.INPUT;

		try {
			getMealService().createMeal(model);
			result = Action.SUCCESS;
		}
		catch (MealAlreadyExistsException e) {
			addFieldError("model.instant", "A meal was already recorded for this hour.");
		}
		catch (MealException e) {
			addActionError(e.getMessage());
		}

		return result;
	}

	public MealRequestSupport getModel() {
		return model;
	}
}

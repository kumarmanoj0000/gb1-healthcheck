package com.gb1.healthcheck.web.meals;

import javax.servlet.http.HttpServletRequest;

import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class CreateMealAction extends MealActionSupport implements Preparable {
	private HttpServletRequest request;
	private MealCreationRequest model;

	public CreateMealAction() {
	}

	public void prepare() {
		loadAvailableFoods();
		model = new BasicMealCreationRequest(getRequester(request));
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

	public MealCreationRequest getModel() {
		return model;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}

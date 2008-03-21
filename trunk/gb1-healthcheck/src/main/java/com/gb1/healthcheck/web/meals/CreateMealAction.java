package com.gb1.healthcheck.web.meals;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

@Controller("createMealAction")
@Scope("prototype")
public class CreateMealAction extends MealActionSupport implements Preparable {
	private MealCreationRequest model;

	public CreateMealAction() {
	}

	public void prepare() {
		loadAvailableFoods();
		model = new BasicMealCreationRequest(getRequester());
	}

	@Override
	public String execute() {
		String result = Action.INPUT;

		try {
			getMealService().createMeal(model);
			setActionMessageKey("meals.edit.success");
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

	public MealCreationRequest getModel() {
		return model;
	}
}

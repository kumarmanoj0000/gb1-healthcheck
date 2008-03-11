package com.gb1.healthcheck.web.meals;

import javax.servlet.http.HttpServletRequest;

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
	private HttpServletRequest request;
	private MealCreationRequest model;
	private String confirmationMessageKey;

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
			confirmationMessageKey = "meals.create.success";
			result = Action.SUCCESS;
		}
		catch (MealAlreadyExistsException e) {
			addFieldError("model.instant", getText("meal.exception.alreadyExists"));
		}
		catch (MealException e) {
			addActionError(getText("meals.create.error", new String[] { e.getMessage() }));
		}

		return result;
	}

	public MealCreationRequest getModel() {
		return model;
	}

	public String getConfirmationMessageKey() {
		return confirmationMessageKey;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}

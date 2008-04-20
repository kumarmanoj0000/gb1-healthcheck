package com.gb1.healthcheck.web.meals;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.domain.meals.MealAlreadyExistsException;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/meals/editMeal.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "manageMeals", params = {
				"namespace", "/meals", "parse", "true", "actionMessageKey", "${actionMessageKey}",
				"refreshList", "true" }) })
@Validation
public class CreateMealAction extends MealActionSupport {
	private User requester;
	private BasicMealCreationRequest model;

	public CreateMealAction() {
	}

	@Override
	public String input() {
		model = new BasicMealCreationRequest(requester);
		return Action.INPUT;
	}

	@Override
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "model.instant", message = "", key = "meals.edit.error.instantRequired") })
	public String execute() {
		String result = Action.INPUT;

		try {
			getMealService().createMeal(getModel());
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

	public BasicMealCreationRequest getModel() {
		return model;
	}

	public void setModel(BasicMealCreationRequest model) {
		this.model = model;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}

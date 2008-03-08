package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Controller("createSimpleFoodAction")
@Scope("prototype")
public class CreateSimpleFoodAction extends SimpleFoodActionSupport {
	private BasicSimpleFoodCreationRequest foodCreationRequest = new BasicSimpleFoodCreationRequest();

	public CreateSimpleFoodAction() {
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "Name is required.") })
	public String submit() {
		String result = Action.INPUT;

		try {
			getFoodService().createSimpleFood(foodCreationRequest);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.simpleFoods.create.error",
					new String[] { e.getMessage() }));
		}

		return result;
	}

	public BasicSimpleFoodCreationRequest getModel() {
		return foodCreationRequest;
	}
}

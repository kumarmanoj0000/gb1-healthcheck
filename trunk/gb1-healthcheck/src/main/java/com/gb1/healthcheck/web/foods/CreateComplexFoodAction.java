package com.gb1.healthcheck.web.foods;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gb1.healthcheck.domain.foods.ComplexFoodHasNoIngredientsException;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Controller("createComplexFoodAction")
@Scope("prototype")
@Validation
public class CreateComplexFoodAction extends ComplexFoodActionSupport {
	private BasicComplexFoodCreationRequest foodCreationRequest = new BasicComplexFoodCreationRequest();
	private String confirmationMessage;

	public CreateComplexFoodAction() {
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "", key = "foods.complexFoods.create.error.nameRequired") })
	public String submit() {
		String result = Action.INPUT;

		try {
			getFoodService().createComplexFood(foodCreationRequest);
			confirmationMessage = getText("foods.complexFoods.create.success");
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", getText("food.exception.alreadyExists"));
		}
		catch (ComplexFoodHasNoIngredientsException e) {
			addFieldError("model.name", getText("food.exception.selectAtLeastOneIngredient"));
		}
		catch (FoodException e) {
			addActionError(getText("foods.complexFoods.create.error",
					new String[] { e.getMessage() }));
		}

		return result;
	}

	public BasicComplexFoodCreationRequest getModel() {
		return foodCreationRequest;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}
}

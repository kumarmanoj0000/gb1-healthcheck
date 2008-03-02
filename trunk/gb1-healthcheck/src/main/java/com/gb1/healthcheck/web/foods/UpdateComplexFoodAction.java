package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.services.foods.FullComplexFoodHydrater;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class UpdateComplexFoodAction extends ComplexFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateComplexFoodAction.class.getName()
			+ ".model";

	private Map<String, Object> session;
	private Long foodId;

	public UpdateComplexFoodAction() {
	}

	@Override
	public String input() {
		ComplexFood food = getFoodService().loadComplexFood(foodId, new FullComplexFoodHydrater());
		BasicComplexFoodUpdateRequest model = new BasicComplexFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "Name is required.") })
	public String submit() {
		String result;

		try {
			getFoodService().updateComplexFood(getModel());
			session.remove(MODEL_SESSION_KEY);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
			result = Action.INPUT;
		}
		catch (FoodException e) {
			addActionError(e.getMessage());
			result = Action.INPUT;
		}

		return result;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public BasicComplexFoodUpdateRequest getModel() {
		BasicComplexFoodUpdateRequest model = (BasicComplexFoodUpdateRequest) session
				.get(MODEL_SESSION_KEY);
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}

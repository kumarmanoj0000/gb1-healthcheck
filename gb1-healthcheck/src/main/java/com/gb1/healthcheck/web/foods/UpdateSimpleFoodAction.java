package com.gb1.healthcheck.web.foods;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.foods.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class UpdateSimpleFoodAction extends SimpleFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateSimpleFoodAction.class.getName()
			+ ".model";

	private Map<String, Object> session;
	private Long foodId = null;

	public UpdateSimpleFoodAction() {
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	@Override
	public String input() {
		SimpleFood food = getFoodService().loadSimpleFood(foodId);
		BasicSimpleFoodUpdateRequest model = new BasicSimpleFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "Name is required.") })
	public String submit() throws FoodException {
		String result;

		try {
			getFoodService().updateSimpleFood(foodId, getModel());
			session.remove(MODEL_SESSION_KEY);
			result = Action.SUCCESS;
		}
		catch (FoodAlreadyExistsException e) {
			addFieldError("model.name", "A food with this name already exists.");
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

	public BasicSimpleFoodUpdateRequest getModel() {
		return (BasicSimpleFoodUpdateRequest) session.get(MODEL_SESSION_KEY);
	}
}

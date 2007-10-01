package com.gb1.healthcheck.web.nutrition;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.opensymphony.xwork2.Action;

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

	public String input() {
		SimpleFood food = getFoodService().loadSimpleFood(foodId);
		SimpleFoodUpdateRequest model = new SimpleFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.SUCCESS;
	}

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

	public SimpleFoodUpdateRequest getModel() {
		return (SimpleFoodUpdateRequest) session.get(MODEL_SESSION_KEY);
	}
}

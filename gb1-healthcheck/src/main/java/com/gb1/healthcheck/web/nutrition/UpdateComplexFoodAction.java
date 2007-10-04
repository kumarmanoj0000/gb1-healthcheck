package com.gb1.healthcheck.web.nutrition;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodAlreadyExistsException;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.opensymphony.xwork2.Action;

public class UpdateComplexFoodAction extends ComplexFoodActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateComplexFoodAction.class.getName()
			+ ".model";

	private Map<String, Object> session;
	private Long foodId;

	public UpdateComplexFoodAction() {
	}

	public String input() {
		ComplexFood food = getFoodService().loadComplexFood(foodId);
		ComplexFoodUpdateRequest model = new ComplexFoodUpdateRequest(food);
		session.put(MODEL_SESSION_KEY, model);

		return Action.SUCCESS;
	}

	public String submit() {
		String result;

		try {
			getFoodService().updateComplexFood(foodId, getModel());
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

	public ComplexFoodUpdateRequest getModel() {
		return (ComplexFoodUpdateRequest) session.get(MODEL_SESSION_KEY);
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}
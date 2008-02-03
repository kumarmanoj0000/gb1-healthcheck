package com.gb1.healthcheck.web.meals;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.MealException;
import com.gb1.healthcheck.services.meals.FullMealHydrater;
import com.gb1.healthcheck.services.meals.MealUpdateRequest;
import com.opensymphony.xwork2.Action;

public class UpdateMealAction extends MealActionSupport implements SessionAware {
	private static final String MODEL_SESSION_KEY = UpdateMealAction.class.getName() + ".model";

	private Map<String, Object> session;
	private Long mealId;

	public UpdateMealAction() {
	}

	@Override
	public String input() {
		Meal meal = getMealService().loadMeal(mealId, new FullMealHydrater());
		MealUpdateRequest model = new BasicMealUpdateRequest(meal);
		session.put(MODEL_SESSION_KEY, model);

		return Action.INPUT;
	}

	public String update() {
		String result;

		try {
			getMealService().updateMeal(mealId, getModel());
			session.remove(MODEL_SESSION_KEY);
			result = Action.SUCCESS;
		}
		catch (MealException e) {
			addActionError(e.getMessage());
			result = Action.INPUT;
		}

		return result;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public MealUpdateRequest getModel() {
		MealUpdateRequest model = (MealUpdateRequest) session.get(MODEL_SESSION_KEY);
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}

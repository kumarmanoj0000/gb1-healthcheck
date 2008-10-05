package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.SessionAware;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public abstract class MealActionSupport extends ActionSupport implements SessionAware {
	protected static final String MODEL_SESSION_KEY = MealActionSupport.class.getName() + ".model";

	@Resource
	protected FoodService foodService;

	@Resource
	protected MealService mealService;

	protected User requester;
	private Map<String, Object> sessionMap;
	private List<Food> availableFoods = null;

	protected MealActionSupport() {
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	protected void saveModel(MealBuilder model) {
		sessionMap.put(MODEL_SESSION_KEY, model);
	}

	protected void unloadModel() {
		sessionMap.remove(MODEL_SESSION_KEY);
	}

	public MealBuilder getModel() {
		return (MealBuilder) sessionMap.get(MODEL_SESSION_KEY);
	}

	public void addDish(HttpSession session) {
		((MealBuilder) session.getAttribute(MODEL_SESSION_KEY)).addDish();
	}

	public int removeDish(HttpSession session, int dishIndex) {
		((MealBuilder) session.getAttribute(MODEL_SESSION_KEY)).removeDish(dishIndex);
		return dishIndex;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		return Arrays.asList(PreparationMethod.values());
	}

	public List<Food> getAvailableFoods() {
		if (availableFoods == null) {
			availableFoods = new LinkedList<Food>();
			availableFoods.addAll(foodService.getSimpleFoods());
			availableFoods.addAll(foodService.getComplexFoods(new IdentityHydrater<ComplexFood>()));
			Collections.sort(availableFoods, new Food.ByNameComparator());
		}

		return availableFoods;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}

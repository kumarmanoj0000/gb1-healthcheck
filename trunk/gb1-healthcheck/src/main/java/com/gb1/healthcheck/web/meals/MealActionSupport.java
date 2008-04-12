package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import com.gb1.commons.dataaccess.IdentityHydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.foods.FoodService;
import com.gb1.healthcheck.services.meals.MealService;
import com.gb1.struts2.security.AuthenticatedUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public abstract class MealActionSupport extends ActionSupport {
	private String actionMessageKey;
	private FoodService foodService;
	private MealService mealService;

	private User requester;
	private Long mealId;
	private List<Food> availableFoods = new LinkedList<Food>();

	protected MealActionSupport() {
	}

	protected void loadAvailableFoods() {
		availableFoods.addAll(getFoodService().getSimpleFoods());
		availableFoods
				.addAll(getFoodService().getComplexFoods(new IdentityHydrater<ComplexFood>()));
		Collections.sort(availableFoods, new Food.ByNameComparator());
	}

	public String cancel() {
		return Action.SUCCESS;
	}

	public List<PreparationMethod> getAvailablePreparationMethods() {
		List<PreparationMethod> methods = Arrays.asList(PreparationMethod.values());
		return Collections.unmodifiableList(methods);
	}

	public List<Food> getAvailableFoods() {
		return Collections.unmodifiableList(availableFoods);
	}

	protected User getRequester() {
		return requester;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}

	public String getActionMessageKey() {
		return actionMessageKey;
	}

	protected void setActionMessageKey(String actionMessageKey) {
		this.actionMessageKey = actionMessageKey;
	}

	protected FoodService getFoodService() {
		return foodService;
	}

	@Resource
	public void setFoodService(FoodService foodSvc) {
		this.foodService = foodSvc;
	}

	protected MealService getMealService() {
		return mealService;
	}

	@Resource
	public void setMealService(MealService mealSvc) {
		this.mealService = mealSvc;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
}
